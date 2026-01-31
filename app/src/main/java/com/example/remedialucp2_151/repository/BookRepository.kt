package com.example.remedialucp2_151.repository

import com.example.remedialucp2_151.room.BookEntity
import com.example.remedialucp2_151.room.BookDao
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooksStream(): Flow<List<BookEntity>>
    fun getBooksInCategoryRecursiveStream(categoryId: Long): Flow<List<BookEntity>>
    suspend fun insertBook(book: BookEntity)
    fun getBookStream(id: Long): Flow<BookEntity?>
    suspend fun updateBook(book: BookEntity)
    suspend fun softDeleteBook(id: Long)
}

class OfflineBookRepository(
    private val bookDao: BookDao
) : BookRepository {
    override fun getAllBooksStream(): Flow<List<BookEntity>> = bookDao.getAllBooks()
    override fun getBooksInCategoryRecursiveStream(categoryId: Long): Flow<List<BookEntity>> = bookDao.getBooksInCategoryRecursive(categoryId)
    override suspend fun insertBook(book: BookEntity) = bookDao.insertBook(book)
    override fun getBookStream(id: Long): Flow<BookEntity?> = bookDao.getBook(id)
    override suspend fun updateBook(book: BookEntity) = bookDao.updateBook(book)
    override suspend fun softDeleteBook(id: Long) = bookDao.softDeleteBook(id)
}