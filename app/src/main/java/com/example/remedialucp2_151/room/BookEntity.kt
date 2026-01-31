package com.example.remedialucp2_151.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val categoryId: Long? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null
)