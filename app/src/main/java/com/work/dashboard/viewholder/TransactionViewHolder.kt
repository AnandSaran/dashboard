package com.work.dashboard.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.work.dashboard.R
import com.work.dashboard.databinding.ItemTransactionDetailBinding
import com.work.dashboard.network.resposne.Data
import com.work.dashboard.util.constants.SYMBOL_MINUS
import com.work.dashboard.util.constants.SYMBOL_SINGLE_SPACE
import com.work.dashboard.util.constants.TRANSACTION_TYPE_TRANSFER

class TransactionViewHolder(private var binding: ItemTransactionDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: Data
    ) {
        binding.apply {
            tvName.text = data.receipient.accountHolder
            tvAccountNo.text = data.receipient.accountNo
            tvAmount.text = getDisplayAmount(data.amount, data.transactionType)
            tvAmount.setTextColor(generateAmountTextColor(data.transactionType, tvAmount.context))
        }
    }


    private fun getDisplayAmount(amount: Double, transactionType: String): String {
        var displayAmount = amount.toString()
        if (transactionType == TRANSACTION_TYPE_TRANSFER) {
            displayAmount = SYMBOL_MINUS + SYMBOL_SINGLE_SPACE + amount
        }
        return displayAmount

    }

    private fun generateAmountTextColor(transactionType: String, context: Context): Int {
        return if (transactionType == TRANSACTION_TYPE_TRANSFER) {
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