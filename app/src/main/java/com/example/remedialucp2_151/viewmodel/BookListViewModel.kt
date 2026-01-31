package com.example.remedialucp2_151.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2_151.repository.BookRepository
import com.example.remedialucp2_151.room.BookEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookListViewModel(private val bookRepository: BookRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT = 5000L
    }

    val uiState: StateFlow<BookListUiState> =
        bookRepository.getAllBooksStream()
            .map { BookListUiState(listBooks = it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT),
                BookListUiState()
            )

    fun getBooksInCategoryRecursive(categoryId: Long): StateFlow<BookListUiState> =
        bookRepository.getBooksInCategoryRecursiveStream(categoryId)
            .map { BookListUiState(listBooks = it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT),
                BookListUiState()
            )
}

data class BookListUiState(
    val listBooks: List<BookEntity> = emptyList()
)