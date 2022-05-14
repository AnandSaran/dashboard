package com.work.dashboard.register.viewmodel

import androidx.lifecycle.*
import com.work.dashboard.base.BaseResult
import com.work.dashboard.network.repository.LoginRepository
import com.work.dashboard.network.request.RegisterRequest
import com.work.dashboard.network.resposne.RegisterResponse
import com.work.dashboard.util.constants.*
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val userName: MutableLiveData<String> = MutableLiveData()
    private val password: MutableLiveData<String> = MutableLiveData()
    private val confirmPassword: MutableLiveData<String> = MutableLiveData()

    val formErrors = MutableLiveData<ArrayList<FormErrors>>()

    fun validateForm(): Boolean {
        val formErrors = ArrayList<FormErrors>()

        if (userName.value.isNullOrEmpty()) {
            formErrors.add(FormErrors.INVALID_USER_NAME)
        }
        if (password.value.isNullOrEmpty()) {
            formErrors.add(FormErrors.INVALID_PASSWORD)
        }
        if (confirmPassword.value.isNullOrEmpty()) {
            formErrors.add(FormErrors.INVALID_CONFIRM_PASSWORD)
        }

        if (password.value != confirmPassword.value) {
            formErrors.add(FormErrors.PASSWORD_NOT_MATCH)
        }
        updateFormErrors(formErrors)
        return formErrors.isEmpty()
    }

    private fun updateFormErrors(value: java.util.ArrayList<FormErrors>) {
        formErrors.value = value
    }

    fun register() =
        liveData(Dispatchers.IO) {
            emit(BaseResult.loading(null))
            val result = loginRepository.register(
                generateRegisterRequest()
            )
            when (result.status) {
                BaseResult.Status.SUCCESS -> {
                    onRegisterSuccess(result)
                }
                BaseResult.Status.ERROR -> {
                    onRegisterFail(result.message)
                }
                else -> {
                }
            }
        }

    private suspend fun LiveDataScope<BaseResult<RegisterResponse>>.onRegisterSuccess(result: BaseResult<RegisterResponse>) {
        result.data?.let {
            emit(BaseResult.success(data = it))
        }
    }

    private suspend fun LiveDataScope<BaseResult<RegisterResponse>>.onRegisterFail(message: String) {
        emit(BaseResult.error(message))
    }

    private fun generateRegisterRequest() = RegisterRequest(
        userName.value ?: EMPTY_STRING,
        password.value ?: EMPTY_STRING,
    )

    fun updateUserName(value: String) {
        userName.value = value
    }

    fun updatePassword(value: String) {
        password.value = value
    }

    fun updateConfirmPassword(value: String) {
        confirmPassword.value = value
    }

    enum class FormErrors(val value: String) {
        INVALID_USER_NAME(ERROR_USER_NAME),
        INVALID_PASSWORD(ERROR_PASSWORD),
        INVALID_CONFIRM_PASSWORD(ERROR_CONFIRM_PASSWORD),
        PASSWORD_NOT_MATCH(ERROR_PASSWORD_NOT_MATCH),
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