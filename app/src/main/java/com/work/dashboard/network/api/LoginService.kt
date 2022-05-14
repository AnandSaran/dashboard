package com.work.dashboard.network.api

import com.work.dashboard.network.request.LoginRequest
import com.work.dashboard.network.resposne.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}