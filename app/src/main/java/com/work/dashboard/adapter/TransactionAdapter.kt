package com.work.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.dashboard.network.resposne.Data
import com.work.dashboard.viewholder.DayTransactionViewHolder
import com.work.dashboard.viewholder.TransactionViewHolder

class TransactionAdapter(
    private val dataList: List<Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = this::class.java.simpleName

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return TransactionViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val data = dataList[position]
        val mHolder = holder as TransactionViewHolder
        mHolder.bind(data)
    }
}