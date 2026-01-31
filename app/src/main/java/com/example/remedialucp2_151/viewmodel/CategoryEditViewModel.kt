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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryEditViewModel(
    private val categoryRepository: CategoryRepository,
    private val auditRepository: AuditRepository
) : ViewModel() {

    var uiState by mutableStateOf(CategoryDetail())
        private set

    fun load(id: Long) = viewModelScope.launch {
        categoryRepository.getCategoryStream(id)
            .filterNotNull()
            .first()
            .let { category ->
                uiState = category.toDetail()
            }
    }

    fun update(onDone: () -> Unit = {}) = viewModelScope.launch {
        if (uiState.isValid) {
            val oldCategory = categoryRepository.getCategoryStream(uiState.id).first()
            val newCategory = uiState.toEntity()
            categoryRepository.insertCategorySafe(newCategory)
            auditRepository.insertLog(AuditLogEntity(
                tableName = "categories",
                operation = "UPDATE",
                oldData = Gson().toJson(oldCategory),
                newData = Gson().toJson(newCategory)
            ))
            onDone()
        }
    }

    fun deleteWithOptions(deleteBooks: Boolean, onDone: () -> Unit = {}) = viewModelScope.launch {
        try {
            val oldCategory = categoryRepository.getCategoryStream(uiState.id).first()
            categoryRepository.deleteCategoryWithOptions(uiState.id, deleteBooks)
            auditRepository.insertLog(AuditLogEntity(
                tableName = "categories",
                operation = "DELETE",
                oldData = Gson().toJson(oldCategory),
                newData = null
            ))
            onDone()
        } catch (e: IllegalStateException) {
            throw e
        }
    }

    private fun CategoryEntity.toDetail() = CategoryDetail(
        id = id,
        name = name,
        parentId = parentId,
        isValid = true
    )
}