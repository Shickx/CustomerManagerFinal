package com.example.customerlist.data

import kotlinx.coroutines.flow.Flow

class CustomerRepository(
    private val dao: CustomerDao
) {
    val customers: Flow<List<CustomerEntity>> = dao.getAllCustomers()

    suspend fun getById(id: Int): CustomerEntity? {
        return dao.getCustomerById(id)
    }

    suspend fun save(customer: CustomerEntity) {
        if (customer.id == 0) {
            dao.insert(customer)
        } else {
            dao.update(customer)
        }
    }

    suspend fun delete(customer: CustomerEntity) {
        dao.delete(customer)
    }
}
