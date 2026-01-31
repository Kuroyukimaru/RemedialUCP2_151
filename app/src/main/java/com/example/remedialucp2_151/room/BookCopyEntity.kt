package com.example.remedialucp2_151.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_copies")
data class BookCopyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: Long,
    val uniqueId: String,
    val status: String
)