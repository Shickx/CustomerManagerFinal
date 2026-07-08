package com.example.customerlist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.customerlist.R
import com.example.customerlist.data.CustomerEntity

class CustomerAdapter(
    private val onCustomerClick: (CustomerEntity) -> Unit
) : ListAdapter<CustomerEntity, CustomerAdapter.CustomerViewHolder>(DiffCallback) {

    private var viewMode: ViewMode = ViewMode.LIST

    fun setViewMode(mode: ViewMode) {
        viewMode = mode
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_customer, parent, false)

        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(
            customer = getItem(position),
            viewMode = viewMode,
            onCustomerClick = onCustomerClick
        )
    }

    class CustomerViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val listContainer: LinearLayout =
            itemView.findViewById(R.id.listContainer)

        private val tableContainer: LinearLayout =
            itemView.findViewById(R.id.tableContainer)

        private val nameText: TextView =
            itemView.findViewById(R.id.textName)

        private val typeText: TextView =
            itemView.findViewById(R.id.textType)

        private val emailText: TextView =
            itemView.findViewById(R.id.textEmail)

        private val phoneText: TextView =
            itemView.findViewById(R.id.textPhone)

        private val tableNameText: TextView =
            itemView.findViewById(R.id.textTableName)

        private val tableTypeText: TextView =
            itemView.findViewById(R.id.textTableType)

        private val tableEmailText: TextView =
            itemView.findViewById(R.id.textTableEmail)

        private val tablePhoneText: TextView =
            itemView.findViewById(R.id.textTablePhone)

        fun bind(
            customer: CustomerEntity,
            viewMode: ViewMode,
            onCustomerClick: (CustomerEntity) -> Unit
        ) {
            if (viewMode == ViewMode.TABLE) {
                listContainer.visibility = View.GONE
                tableContainer.visibility = View.VISIBLE

                tableNameText.text = customer.name
                tableTypeText.text = customer.type.name
                tableEmailText.text = customer.email
                tablePhoneText.text = customer.phone
            } else {
                listContainer.visibility = View.VISIBLE
                tableContainer.visibility = View.GONE

                nameText.text = customer.name
                typeText.text = customer.type.name
                emailText.text = "Email: ${customer.email}"
                phoneText.text = "Phone: ${customer.phone}"
            }

            itemView.setOnClickListener {
                onCustomerClick(customer)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<CustomerEntity>() {

        override fun areItemsTheSame(
            oldItem: CustomerEntity,
            newItem: CustomerEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CustomerEntity,
            newItem: CustomerEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}