package com.work.dashboard.network.api

import com.work.dashboard.network.request.LoginRequest
import com.work.dashboard.network.request.RegisterRequest
import com.work.dashboard.network.resposne.LoginResponse
import com.work.dashboard.network.resposne.RegisterResponse
import com.work.dashboard.util.constants.URL_LOGIN
import com.work.dashboard.util.constants.URL_REGISTER
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST(URL_LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST(URL_REGISTER)
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
}