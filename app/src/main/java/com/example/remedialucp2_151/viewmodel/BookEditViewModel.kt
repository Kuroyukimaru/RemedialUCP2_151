package com.example.remedialucp2_151.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2_151.repository.AuditRepository
import com.example.remedialucp2_151.repository.BookRepository
import com.example.remedialucp2_151.room.AuditLogEntity
import com.example.remedialucp2_151.room.BookEntity
import com.google.gson.Gson
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookEditViewModel(
    private val bookRepository: BookRepository,
    private val auditRepository: AuditRepository
) : ViewModel() {

    var uiState by mutableStateOf(BookDetail())
        private set

    fun load(id: Long) = viewModelScope.launch {
        bookRepository.getBookStream(id)
            .filterNotNull()
            .first()
            .let { book ->
                uiState = book.toDetail()
            }
    }

    fun updateState(newState: BookDetail) {
        uiState = newState.copy(
            isValid = newState.title.isNotBlank() && (newState.categoryId == null || newState.categoryId > 0)
        )
    }

    fun update(onDone: () -> Unit = {}) = viewModelScope.launch {
        if (uiState.isValid) {
            val oldBook = bookRepository.getBookStream(uiState.id).first()
            val newBook = uiState.toEntity()
            bookRepository.updateBook(newBook)
            auditRepository.insertLog(AuditLogEntity(
                tableName = "books",
                operation = "UPDATE",
                oldData = Gson().toJson(oldBook),
                newData = Gson().toJson(newBook)
            ))
            onDone()
        }
    }

    private fun BookEntity.toDetail() = BookDetail(
        id = id,
        title = title,
        categoryId = categoryId,
        isValid = true
    )
}