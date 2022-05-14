package com.work.dashboard.login.view.activity

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.work.dashboard.R
import com.work.dashboard.base.BaseActivity
import com.work.dashboard.base.BaseResult
import com.work.dashboard.dashboard.view.activity.DashboardActivity
import com.work.dashboard.databinding.ActivityLoginBinding
import com.work.dashboard.login.viiewmodel.RegisterViewModel
import com.work.dashboard.login.viiewmodel.RegisterViewModel.FormErrors.INVALID_PASSWORD
import com.work.dashboard.login.viiewmodel.RegisterViewModel.FormErrors.INVALID_USER_NAME
import com.work.dashboard.network.repository.LoginRepository
import com.work.dashboard.network.resposne.LoginResponse
import com.work.dashboard.register.view.RegisterActivity
import com.work.dashboard.util.constants.BUNDLE_KEY_TOKEN
import com.work.dashboard.util.constants.BUNDLE_KEY_USER_ACCOUNT_NO
import com.work.dashboard.util.constants.BUNDLE_KEY_USER_NAME
import com.work.dashboard.util.constants.VIEW_MODEL_IN_ACCESSIBLE_MESSAGE

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelFactory: RegisterViewModel.Factory
    private val viewModel: RegisterViewModel by lazy {
        requireNotNull(this) {
            VIEW_MODEL_IN_ACCESSIBLE_MESSAGE
        }
        ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.login)
        setupOnClickListener()
        setupViewModelFactory()
        setupViewModelObserver()
        setupTextChangeListener()
    }

    private fun setupTextChangeListener() {
        binding.edtUserName.addTextChangedListener { text ->
            text?.let {
                binding.tilUserName.error = null
                viewModel.updateUserName(text.toString())
            }
        }
        binding.edtPassword.addTextChangedListener { text ->
            text?.let {
                binding.tilPassword.error = null
                viewModel.updatePassword(text.toString())
            }
        }
    }

    private fun setupViewModelObserver() {
        viewModel.formErrors.observe(this, Observer { formErrors ->
            formErrors?.let {
                formErrors.forEach { formError ->
                    when (formError) {
                        INVALID_USER_NAME -> {
                            binding.tilUserName.error = INVALID_USER_NAME.value
                        }
                        INVALID_PASSWORD -> {
                            binding.tilPassword.error = INVALID_PASSWORD.value

                        }
                    }
                }
            }
        })
    }

    private fun setupOnClickListener() {
        binding.btnLogin.setOnClickListener {
            onClickLogin()
        }
        binding.btnRegister.setOnClickListener {
            onClickRegister()
        }
    }

    private fun onClickRegister() {
        RegisterActivity.present(this)
    }

    private fun setupViewModelFactory() {
        viewModelFactory = RegisterViewModel.Factory(LoginRepository())
    }

    private fun onClickLogin() {
        if (viewModel.validateForm()) {
            viewModel.login().observe(this, Observer {
                when (it.status) {
                    BaseResult.Status.SUCCESS -> {
                        dismissProgressBar()
                        showDashboardActivity(it)
                    }
                    BaseResult.Status.ERROR -> {
                        dismissProgressBar()
                        showSnackBar(it.message)
                    }
                    BaseResult.Status.LOADING -> {
                        showProgressBar()
                    }
                }
            })
        }
    }

    private fun showDashboardActivity(loginResult: BaseResult<LoginResponse>) {
        loginResult.data?.let { loginResponse ->
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_TOKEN, loginResponse.token)
            bundle.putString(BUNDLE_KEY_USER_NAME, loginResponse.username)
            bundle.putString(BUNDLE_KEY_USER_ACCOUNT_NO, loginResponse.accountNo)
            DashboardActivity.present(this, bundle)
            finish()
        }
    }
}