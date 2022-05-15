package com.work.dashboard.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.work.dashboard.R
import com.work.dashboard.databinding.ItemTransactionDetailBinding
import com.work.dashboard.network.resposne.Data
import com.work.dashboard.util.constants.*

class TransactionViewHolder(private var binding: ItemTransactionDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: Data
    ) {
        binding.apply {
            tvName.text = generateName(data)
            tvAccountNo.text = generateAccountNo(data)
            tvAmount.text = generateAmount(data)
            tvAmount.setTextColor(generateAmountTextColor(data, tvAmount.context))
        }
    }

    private fun generateName(data: Data): String {
        return when (data.transactionType) {
            TRANSACTION_TYPE_TRANSFER -> {
                data.receipient?.accountHolder ?: EMPTY_STRING
            }
            TRANSACTION_TYPE_RECEIVED -> {
                data.sender?.accountHolder ?: EMPTY_STRING
            }
            else -> {
                EMPTY_STRING
            }
        }
    }

    private fun generateAccountNo(data: Data): String {
        return when (data.transactionType) {
            TRANSACTION_TYPE_TRANSFER -> {
                data.receipient?.accountNo ?: EMPTY_STRING
            }
            TRANSACTION_TYPE_RECEIVED -> {
                data.sender?.accountNo ?: EMPTY_STRING
            }
            else -> {
                EMPTY_STRING
            }
        }
    }


    private fun generateAmount(data: Data): String {
        var displayAmount = data.amount.toString()
        if (data.transactionType == TRANSACTION_TYPE_TRANSFER) {
            displayAmount = SYMBOL_MINUS + SYMBOL_SINGLE_SPACE + displayAmount
        }
        return displayAmount
    }

    private fun generateAmountTextColor(data: Data, context: Context): Int {
        return if (data.transactionType == TRANSACTION_TYPE_TRANSFER) {
            ContextCompat.getColor(
                context,
                R.color.grey_dark
            )
        } else {
            ContextCompat.getColor(context, R.color.green)
        }
    }

    companion object {
        fun from(parent: ViewGroup): TransactionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTransactionDetailBinding.inflate(layoutInflater, parent, false)
            return TransactionViewHolder(binding)
        }
    }
}