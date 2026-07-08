package com.example.customerlist.presentation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.customerlist.CustomerListApplication
import com.example.customerlist.R
import com.example.customerlist.data.CustomerEntity
import com.example.customerlist.data.CustomerType
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class EditCustomerActivity : AppCompatActivity() {

    private val viewModel: CustomersViewModel by viewModels {
        (application as CustomerListApplication).container.viewModelFactory
    }

    private var customerId: Int = 0
    private var fixedType: CustomerType? = null

    private lateinit var typeSpinner: Spinner
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_customer)

        customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, 0)

        bindViews()
        setupToolbar()
        setupTypeSpinner()
        setupSaveButton()

        if (customerId != 0) {
            loadCustomer()
        }
    }

    private fun bindViews() {
        typeSpinner = findViewById(R.id.spinnerType)
        nameInput = findViewById(R.id.inputName)
        emailInput = findViewById(R.id.inputEmail)
        phoneInput = findViewById(R.id.inputPhone)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = if (customerId == 0) "Add customer" else "Edit customer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupTypeSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            CustomerType.values().map { it.name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = adapter
    }

    private fun setupSaveButton() {
        findViewById<MaterialButton>(R.id.buttonSave).setOnClickListener {
            saveCustomer()
        }
    }

    private fun loadCustomer() {
        lifecycleScope.launch {
            val customer = viewModel.getById(customerId)
            if (customer == null) {
                finish()
                return@launch
            }

            fixedType = customer.type
            typeSpinner.setSelection(CustomerType.values().indexOf(customer.type))
            typeSpinner.isEnabled = false
            nameInput.setText(customer.name)
            emailInput.setText(customer.email)
            phoneInput.setText(customer.phone)
        }
    }

    private fun saveCustomer() {
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()

        if (name.isBlank() || email.isBlank() || phone.isBlank()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val type = fixedType ?: CustomerType.values()[typeSpinner.selectedItemPosition]
        val customer = CustomerEntity(
            id = customerId,
            type = type,
            name = name,
            email = email,
            phone = phone
        )

        viewModel.save(customer)
        finish()
    }

    companion object {
        const val EXTRA_CUSTOMER_ID = "extra_customer_id"
    }
}
