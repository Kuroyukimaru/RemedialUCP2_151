package com.example.remedialucp2_151.room

import androidx.room.Entity

@Entity(tableName = "book_authors", primaryKeys = ["bookId", "authorId"])
data class BookAuthorEntity(
    val bookId: Long,
    val authorId: Long
)