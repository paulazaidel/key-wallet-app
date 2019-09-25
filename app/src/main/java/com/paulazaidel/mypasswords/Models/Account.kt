package com.paulazaidel.mypasswords.Models

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "accounts")
class Account (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Long,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "url")
    val url: String
)