package com.example.customerlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customerlist.data.CustomerEntity
import com.example.customerlist.data.CustomerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CustomersViewModel(
    private val repository: CustomerRepository
) : ViewModel() {

    val customers: StateFlow<List<CustomerEntity>> =
        repository.customers.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    suspend fun getById(id: Int): CustomerEntity? {
        return repository.getById(id)
    }

    fun save(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.save(customer)
        }
    }

    fun delete(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.delete(customer)
        }
    }
}
