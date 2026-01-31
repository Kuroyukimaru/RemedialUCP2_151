package com.example.remedialucp2_151.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>


    @Query("""
        WITH RECURSIVE parent_chain AS (
            SELECT id, parentId FROM categories WHERE id = :parentId
            UNION ALL
            SELECT c.id, c.parentId FROM categories c
            INNER JOIN parent_chain pc ON c.id = pc.parentId
        )
        SELECT COUNT(*) FROM parent_chain WHERE id = :childId
    """)
    suspend fun isCyclic(childId: Long, parentId: Long): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Transaction
    suspend fun insertCategorySafe(category: CategoryEntity): Long {
        if (category.parentId != null && isCyclic(category.id, category.parentId) > 0) {
            throw IllegalArgumentException("Cyclic reference detected!")
        }
        return insertCategory(category)
    }

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategory(id: Long): Flow<CategoryEntity?>


    @Transaction
    suspend fun deleteCategoryWithOptions(id: Long, deleteBooks: Boolean) {
        val borrowedCount = getBorrowedBooksInCategory(id)
        if (borrowedCount > 0) {
            throw IllegalStateException("Cannot delete: category has borrowed books")
        }
        if (deleteBooks) {
            softDeleteBooksInCategory(id)
        } else {
            setBooksToNoCategory(id)
        }
        softDeleteCategory(id)
    }

    @Query("SELECT COUNT(*) FROM book_copies bc INNER JOIN books b ON bc.bookId = b.id WHERE b.categoryId = :categoryId AND bc.status = 'borrowed'")
    suspend fun getBorrowedBooksInCategory(categoryId: Long): Int

    @Query("UPDATE books SET isDeleted = 1, deletedAt = :timestamp WHERE categoryId = :categoryId")
    suspend fun softDeleteBooksInCategory(categoryId: Long, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE books SET categoryId = NULL WHERE categoryId = :categoryId")
    suspend fun setBooksToNoCategory(categoryId: Long)

    @Query("UPDATE categories SET isDeleted = 1, deletedAt = :timestamp WHERE id = :id")
    suspend fun softDeleteCategory(id: Long, timestamp: Long = System.currentTimeMillis())
}