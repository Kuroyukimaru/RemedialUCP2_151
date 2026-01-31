package com.example.remedialucp2_151.repository

import com.example.remedialucp2_151.room.CategoryEntity
import com.example.remedialucp2_151.room.CategoryDao
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategoriesStream(): Flow<List<CategoryEntity>>
    suspend fun insertCategorySafe(category: CategoryEntity): Long
    fun getCategoryStream(id: Long): Flow<CategoryEntity?>
    suspend fun deleteCategoryWithOptions(id: Long, deleteBooks: Boolean)
}

class OfflineCategoryRepository(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getAllCategoriesStream(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
    override suspend fun insertCategorySafe(category: CategoryEntity): Long = categoryDao.insertCategorySafe(category)
    override fun getCategoryStream(id: Long): Flow<CategoryEntity?> = categoryDao.getCategory(id)
    override suspend fun deleteCategoryWithOptions(id: Long, deleteBooks: Boolean) = categoryDao.deleteCategoryWithOptions(id, deleteBooks)
}