package com.example.remedialucp2_151.repository

import com.example.remedialucp2_151.room.AuditLogEntity
import com.example.remedialucp2_151.room.AuditDao
import kotlinx.coroutines.flow.Flow

interface AuditRepository {
    fun getAllLogsStream(): Flow<List<AuditLogEntity>>
    suspend fun insertLog(log: AuditLogEntity)
}

class OfflineAuditRepository(
    private val auditDao: AuditDao
) : AuditRepository {
    override fun getAllLogsStream(): Flow<List<AuditLogEntity>> = auditDao.getAllLogs()
    override suspend fun insertLog(log: AuditLogEntity) = auditDao.insertLog(log)
}