package com.example.remedialucp2_151.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)