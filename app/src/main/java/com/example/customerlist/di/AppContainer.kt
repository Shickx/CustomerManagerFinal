package com.example.customerlist.di

import android.content.Context
import com.example.customerlist.data.CustomerDatabase
import com.example.customerlist.data.CustomerRepository
import com.example.customerlist.presentation.CustomersViewModelFactory

class AppContainer(context: Context) {

    private val database = CustomerDatabase.getInstance(context)

    private val repository = CustomerRepository(
        database.customerDao()
    )

    val viewModelFactory = CustomersViewModelFactory(repository)
}
