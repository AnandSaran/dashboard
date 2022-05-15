package com.work.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.dashboard.network.resposne.Data
import com.work.dashboard.viewholder.DayTransactionViewHolder

class DayTransactionAdapter(
    private val transactionMap: Map<String, List<Data>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = this::class.java.simpleName

    override fun getItemCount(): Int {
        return transactionMap.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return DayTransactionViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val date = transactionMap.keys.toList()[position]
        val data = transactionMap.get<Any, List<Data>>(date) ?: emptyList()
        val mHolder = holder as DayTransactionViewHolder
        mHolder.bind(date, data)
    }
}