package com.example.customerlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customerlist.presentation.CustomerAdapter
import com.example.customerlist.presentation.CustomersViewModel
import com.example.customerlist.presentation.DetailActivity
import com.example.customerlist.presentation.EditCustomerActivity
import com.example.customerlist.presentation.ViewMode
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: CustomersViewModel by viewModels {
        (application as CustomerListApplication).container.viewModelFactory
    }

    private lateinit var adapter: CustomerAdapter
    private var viewMode = ViewMode.LIST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setupInsets()
        setupToolbar()
        setupList()
        setupActions()
        updateViewMode()
        observeCustomers()
    }

    private fun setupInsets() {
        val root = findViewById<android.view.View>(R.id.mainRoot)

        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Customers"
    }

    private fun setupList() {
        adapter = CustomerAdapter { customer ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_CUSTOMER_ID, customer.id)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCustomers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupActions() {
        findViewById<FloatingActionButton>(R.id.buttonAdd).setOnClickListener {
            startActivity(Intent(this, EditCustomerActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.buttonToggleMode).setOnClickListener {
            viewMode = if (viewMode == ViewMode.LIST) {
                ViewMode.TABLE
            } else {
                ViewMode.LIST
            }

            updateViewMode()
        }
    }

    private fun updateViewMode() {
        adapter.setViewMode(viewMode)

        findViewById<android.view.View>(R.id.tableHeader).isVisible =
            viewMode == ViewMode.TABLE

        findViewById<MaterialButton>(R.id.buttonToggleMode).text =
            if (viewMode == ViewMode.LIST) {
                "Table view"
            } else {
                "List view"
            }
    }

    private fun observeCustomers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customers.collect { customers ->
                    adapter.submitList(customers)

                    findViewById<android.view.View>(R.id.textEmpty).isVisible =
                        customers.isEmpty()
                }
            }
        }
    }
}