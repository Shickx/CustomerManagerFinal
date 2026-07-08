package com.example.customerlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: CustomerType,
    val name: String,
    val email: String,
    val phone: String
)
