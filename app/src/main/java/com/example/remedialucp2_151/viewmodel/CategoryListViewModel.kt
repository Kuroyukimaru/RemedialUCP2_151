package com.example.remedialucp2_151.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2_151.repository.CategoryRepository
import com.example.remedialucp2_151.room.CategoryEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CategoryListViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT = 5000L
    }

    val uiState: StateFlow<CategoryListUiState> =
        categoryRepository.getAllCategoriesStream()
            .map { CategoryListUiState(listCategories = it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT),
                CategoryListUiState()
            )
}

data class CategoryListUiState(
    val listCategories: List<CategoryEntity> = emptyList()
)