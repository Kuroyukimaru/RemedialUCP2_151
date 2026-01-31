package com.example.remedialucp2_151.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books WHERE isDeleted = 0 ORDER BY title ASC")
    fun getAllBooks(): Flow<List<BookEntity>>


    @Query("""
        WITH RECURSIVE category_tree AS (
            SELECT id FROM categories WHERE id = :categoryId
            UNION ALL
            SELECT c.id FROM categories c
            INNER JOIN category_tree ct ON c.parentId = ct.id
        )
        SELECT * FROM books WHERE categoryId IN (SELECT id FROM category_tree) AND isDeleted = 0
    """)
    fun getBooksInCategoryRecursive(categoryId: Long): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: BookEntity)

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBook(id: Long): Flow<BookEntity?>

    @Update
    suspend fun updateBook(book: BookEntity)

    @Query("UPDATE books SET isDeleted = 1, deletedAt = :timestamp WHERE id = :id")
    suspend fun softDeleteBook(id: Long, timestamp: Long = System.currentTimeMillis())
}