package com.example.customerlist.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromCustomerType(type: CustomerType): String {
        return type.name
    }

    @TypeConverter
    fun toCustomerType(value: String): CustomerType {
        return CustomerType.valueOf(value)
    }
}
