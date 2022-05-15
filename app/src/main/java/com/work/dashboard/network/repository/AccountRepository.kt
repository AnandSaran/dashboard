package com.work.dashboard.network.repository

import com.work.dashboard.base.BaseRepository
import com.work.dashboard.network.RetrofitClient
import com.work.dashboard.network.api.AccountService


class AccountRepository : BaseRepository() {
    private val accountService = RetrofitClient.retrofit.create(AccountService::class.java)

    suspend fun balance(token: String) = getResult {
        accountService.balance(token)
    }

    suspend fun transactions(token: String) = getResult {
        accountService.transactions(token)
    }
}