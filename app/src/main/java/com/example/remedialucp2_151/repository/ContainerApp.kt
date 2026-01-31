package com.example.remedialucp2_151.repository

import android.content.Context
import com.example.remedialucp2_151.room.AppDatabase

interface ContainerApp {
    val bookRepository: BookRepository
    val categoryRepository: CategoryRepository
    val auditRepository: AuditRepository
}

class ContainerDataApp(private val context: Context) : ContainerApp {
    private val database by lazy {
        AppDatabase.getDatabase(context)
    }

    override val bookRepository: BookRepository by lazy {
        OfflineBookRepository(database.bookDao())
    }

    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(database.categoryDao())
    }

    override val auditRepository: AuditRepository by lazy {
        OfflineAuditRepository(database.auditDao())
    }
}