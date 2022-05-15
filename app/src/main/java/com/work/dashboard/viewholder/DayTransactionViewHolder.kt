package com.work.dashboard.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.dashboard.adapter.TransactionAdapter
import com.work.dashboard.databinding.ItemDayTransactionsBinding
import com.work.dashboard.network.resposne.Data

class DayTransactionViewHolder(private var binding: ItemDayTransactionsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        date: String,
        transactions: List<Data>
    ) {
        binding.apply {
            tvDate.text = date
            rvDayTransactions.apply {
                adapter = TransactionAdapter(transactions)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): DayTransactionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemDayTransactionsBinding.inflate(layoutInflater, parent, false)
            return DayTransactionViewHolder(binding)
        }
    }
}