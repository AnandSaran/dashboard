package com.work.dashboard.network.api

import com.work.dashboard.network.resposne.BalanceResponse
import com.work.dashboard.network.resposne.TransactionResponse
import com.work.dashboard.util.constants.URL_BALANCE
import com.work.dashboard.util.constants.URL_TRANSACTIONS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AccountService {
    @GET(URL_BALANCE)
    suspend fun balance(@Header("Authorization") token: String): Response<BalanceResponse>

    @GET(URL_TRANSACTIONS)
    suspend fun transactions(@Header("Authorization") token: String): Response<TransactionResponse>
}