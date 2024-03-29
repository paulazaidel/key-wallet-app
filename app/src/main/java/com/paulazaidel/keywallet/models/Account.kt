package com.paulazaidel.keywallet.models

import androidx.annotation.NonNull
import androidx.room.*
import java.io.Serializable

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
) : Serializable