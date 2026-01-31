package com.example.remedialucp2_151.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2_151.repository.AuditRepository
import com.example.remedialucp2_151.room.AuditLogEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AuditListViewModel(private val auditRepository: AuditRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT = 5000L
    }

    val uiState: StateFlow<AuditListUiState> =
        auditRepository.getAllLogsStream()
            .map { AuditListUiState(listLogs = it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT),
                AuditListUiState()
            )
}

data class AuditListUiState(
    val listLogs: List<AuditLogEntity> = emptyList()
)