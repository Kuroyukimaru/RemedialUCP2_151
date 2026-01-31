package com.example.remedialucp2_151.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tableName: String,
    val operation: String,
    val oldData: String?,
    val newData: String?,
    val timestamp: Long = System.currentTimeMillis()
)