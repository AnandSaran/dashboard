package com.work.dashboard.network.api

import com.work.dashboard.network.resposne.BalanceResponse
import com.work.dashboard.network.resposne.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AccountService {
    @GET("balance")
    suspend fun balance(@Header("Authorization") token: String): Response<BalanceResponse>

    @GET("transactions")
    suspend fun transactions(@Header("Authorization") token: String): Response<TransactionResponse>
}