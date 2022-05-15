package com.work.dashboard.dashboard.view.viewmodel

import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.work.dashboard.base.BaseResult
import com.work.dashboard.network.repository.AccountRepository
import com.work.dashboard.network.resposne.BalanceResponse
import com.work.dashboard.network.resposne.Data
import com.work.dashboard.network.resposne.TransactionResponse
import com.work.dashboard.util.constants.*
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    fun fetchBalance(userToken: String) =
        liveData(Dispatchers.IO) {
            emit(BaseResult.loading(null))
            val result = accountRepository.balance(userToken)
            when (result.status) {
                BaseResult.Status.SUCCESS -> {
                    onFetchBalanceSuccess(result)
                }
                BaseResult.Status.ERROR -> {
                    onBalanceFail(result.message)
                }
                else -> {
                }
            }
        }

    fun fetchTransAction(userToken: String) =
        liveData(Dispatchers.IO) {
            emit(BaseResult.loading(null))
            val result = accountRepository.transactions(userToken)
            when (result.status) {
                BaseResult.Status.SUCCESS -> {
                    onFetchTransactionSuccess(result)
                }
                BaseResult.Status.ERROR -> {
                    onTransactionFail(result.message)
                }
                else -> {
                }
            }
        }

    private suspend fun LiveDataScope<BaseResult<String>>.onFetchBalanceSuccess(result: BaseResult<BalanceResponse>) {
        result.data?.let {
            emit(BaseResult.success(data = CURRENCY_SGD + SYMBOL_SINGLE_SPACE + it.balance))
        }
    }

    private suspend fun LiveDataScope<BaseResult<BalanceResponse>>.onBalanceFail(message: String) {
        emit(BaseResult.error(message))
    }

    private suspend fun LiveDataScope<BaseResult<Map<String, List<Data>>>>.onFetchTransactionSuccess(
        result: BaseResult<TransactionResponse>
    ) {
        result.data?.let { transactionResponse ->
            val groups = transactionResponse.data.groupBy { data ->
                val dateTimeFormat = SimpleDateFormat(DP_DATE_TIME, Locale.getDefault())
                val dateFormat = SimpleDateFormat(DP_DATE, Locale.getDefault())

                val date = dateTimeFormat.parse(data.transactionDate)
                date?.let {
                    dateFormat.format(date)
                } ?: data.transactionDate
            }
            emit(BaseResult.success(data = groups))
        }
    }

    private suspend fun LiveDataScope<BaseResult<TransactionResponse>>.onTransactionFail(message: String) {
        emit(BaseResult.error(message))
    }

    @Suppress(ANNOTATION_UNCHECKED_CAST)
    class Factory(private val accountRepository: AccountRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(accountRepository) as T
            }
            throw IllegalArgumentException(UNKNOWN_VIEW_MODEL_CLASS)
        }
    }

}