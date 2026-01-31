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
import kotlinx.coroutines.launch

class BookEntryViewModel(
    private val bookRepository: BookRepository,
    private val auditRepository: AuditRepository
) : ViewModel() {

    var uiState by mutableStateOf(BookDetail())
        private set

    fun updateState(newState: BookDetail) {
        uiState = newState.copy(
            isValid = newState.title.isNotBlank() && (newState.categoryId == null || newState.categoryId > 0)
        )
    }

    fun insert(onDone: () -> Unit = {}) = viewModelScope.launch {
        if (uiState.isValid) {
            val book = uiState.toEntity()
            bookRepository.insertBook(book)
            auditRepository.insertLog(AuditLogEntity(
                tableName = "books",
                operation = "INSERT",
                oldData = null,
                newData = Gson().toJson(book)
            ))
            onDone()
        }
    }
}

data class BookDetail(
    val id: Long = 0,
    val title: String = "",
    val categoryId: Long? = null,
    val isValid: Boolean = false
)

fun BookDetail.toEntity() = BookEntity(
    id = id,
    title = title,
    categoryId = categoryId
)