package com.example.remedialucp2_151.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2_151.repository.AuditRepository
import com.example.remedialucp2_151.repository.CategoryRepository
import com.example.remedialucp2_151.room.AuditLogEntity
import com.example.remedialucp2_151.room.CategoryEntity
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CategoryEntryViewModel(
    private val categoryRepository: CategoryRepository,
    private val auditRepository: AuditRepository
) : ViewModel() {

    var uiState by mutableStateOf(CategoryDetail())
        private set

    fun updateState(newState: CategoryDetail) {
        uiState = newState.copy(
            isValid = newState.name.isNotBlank() && (newState.parentId == null || newState.parentId > 0)
        )
    }

    fun insert(onDone: () -> Unit = {}) = viewModelScope.launch {
        if (uiState.isValid) {
            val category = uiState.toEntity()
            categoryRepository.insertCategorySafe(category)
            auditRepository.insertLog(AuditLogEntity(
                tableName = "categories",
                operation = "INSERT",
                oldData = null,
                newData = Gson().toJson(category)
            ))
            onDone()
        }
    }
}

data class CategoryDetail(
    val id: Long = 0,
    val name: String = "",
    val parentId: Long? = null,
    val isValid: Boolean = false
)

fun CategoryDetail.toEntity() = CategoryEntity(
    id = id,
    name = name,
    parentId = parentId
)