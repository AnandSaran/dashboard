package com.work.dashboard.network.repository

import com.work.dashboard.base.BaseRepository
import com.work.dashboard.network.RetrofitClient
import com.work.dashboard.network.api.LoginService
import com.work.dashboard.network.request.LoginRequest
import com.work.dashboard.network.request.RegisterRequest


class LoginRepository : BaseRepository() {
    private val loginService = RetrofitClient.retrofit.create(LoginService::class.java)

    suspend fun login(loginRequest: LoginRequest)= getResult {
        loginService.login(loginRequest)
    }

    suspend fun register(registerRequest: RegisterRequest)= getResult {
        loginService.register(registerRequest)
    }


}