package com.example.customerlist.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.customerlist.CustomerListApplication
import com.example.customerlist.R
import com.example.customerlist.data.CustomerEntity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private val viewModel: CustomersViewModel by viewModels {
        (application as CustomerListApplication).container.viewModelFactory
    }

    private var customerId: Int = 0
    private var currentCustomer: CustomerEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, 0)
        setupToolbar()
        setupButtons()
    }

    override fun onResume() {
        super.onResume()
        loadCustomer()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Customer details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupButtons() {
        findViewById<MaterialButton>(R.id.buttonEdit).setOnClickListener {
            val intent = Intent(this, EditCustomerActivity::class.java)
            intent.putExtra(EditCustomerActivity.EXTRA_CUSTOMER_ID, customerId)
            startActivity(intent)
        }

        findViewById<MaterialButton>(R.id.buttonDelete).setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun loadCustomer() {
        lifecycleScope.launch {
            val customer = viewModel.getById(customerId)
            if (customer == null) {
                finish()
                return@launch
            }

            currentCustomer = customer
            findViewById<TextView>(R.id.textName).text = customer.name
            findViewById<TextView>(R.id.textType).text = customer.type.name
            findViewById<TextView>(R.id.textEmail).text = customer.email
            findViewById<TextView>(R.id.textPhone).text = customer.phone
        }
    }

    private fun showDeleteDialog() {
        val customer = currentCustomer ?: return
        AlertDialog.Builder(this)
            .setTitle("Delete customer")
            .setMessage("Delete ${customer.name}?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.delete(customer)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    companion object {
        const val EXTRA_CUSTOMER_ID = "extra_customer_id"
    }
}
