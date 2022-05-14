package com.work.dashboard.login.viiewmodel

import androidx.lifecycle.*
import com.work.dashboard.base.BaseResult
import com.work.dashboard.network.repository.LoginRepository
import com.work.dashboard.network.request.LoginRequest
import com.work.dashboard.network.resposne.LoginResponse
import com.work.dashboard.util.constants.*
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val userName: MutableLiveData<String> = MutableLiveData()
    private val password: MutableLiveData<String> = MutableLiveData()

    val formErrors = MutableLiveData<ArrayList<FormErrors>>()

    fun validateForm(): Boolean {
        val formErrors = ArrayList<FormErrors>()

        if (userName.value.isNullOrEmpty()) {
            formErrors.add(FormErrors.INVALID_USER_NAME)
        }
        if (password.value.isNullOrEmpty()) {
            formErrors.add(FormErrors.INVALID_PASSWORD)
        }
        updateFormErrors(formErrors)
        return formErrors.isEmpty()
    }

    private fun updateFormErrors(value: java.util.ArrayList<FormErrors>) {
        formErrors.value = value
    }

    fun login() =
        liveData(Dispatchers.IO) {
            emit(BaseResult.loading(null))
            val result = loginRepository.login(
                generateLoginRequest()
            )
            when (result.status) {
                BaseResult.Status.SUCCESS -> {
                    onLoginSuccess(result)
                }
                BaseResult.Status.ERROR -> {
                    onLoginFail(result.message)
                }
                else -> {
                }
            }
        }

    private suspend fun LiveDataScope<BaseResult<LoginResponse>>.onLoginSuccess(result: BaseResult<LoginResponse>) {
        result.data?.let {
            emit(BaseResult.success(data = it))
        }
    }

    private suspend fun LiveDataScope<BaseResult<LoginResponse>>.onLoginFail(message: String) {
        emit(BaseResult.error(message))
    }

    private fun generateLoginRequest() = LoginRequest(
        userName.value ?: EMPTY_STRING,
        password.value ?: EMPTY_STRING,
    )

    fun updateUserName(value: String) {
        userName.value = value
    }

    fun updatePassword(value: String) {
        password.value = value
    }

    enum class FormErrors(val value: String) {
        INVALID_USER_NAME(ERROR_USER_NAME),
        INVALID_PASSWORD(ERROR_PASSWORD),
    }

    @Suppress(ANNOTATION_UNCHECKED_CAST)
    class Factory(private val loginRepository: LoginRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(loginRepository) as T
            }
            throw IllegalArgumentException(UNKNOWN_VIEW_MODEL_CLASS)
        }
    }

}