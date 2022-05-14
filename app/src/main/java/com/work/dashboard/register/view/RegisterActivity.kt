package com.work.dashboard.register.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.work.dashboard.R
import com.work.dashboard.base.BaseActivity
import com.work.dashboard.base.BaseResult
import com.work.dashboard.databinding.ActivityRegisterBinding
import com.work.dashboard.network.repository.LoginRepository
import com.work.dashboard.network.resposne.RegisterResponse
import com.work.dashboard.register.viewmodel.RegisterViewModel
import com.work.dashboard.util.constants.MSG_REGISTER_SUCCESS
import com.work.dashboard.util.constants.VIEW_MODEL_IN_ACCESSIBLE_MESSAGE

class RegisterActivity : BaseActivity() {
    companion object {
        fun present(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelFactory: RegisterViewModel.Factory
    private val viewModel: RegisterViewModel by lazy {
        requireNotNull(this) {
            VIEW_MODEL_IN_ACCESSIBLE_MESSAGE
        }
        ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.register)
        setupToolbar()
        setupOnClickListener()
        setupViewModelFactory()
        setupViewModelObserver()
        setupTextChangeListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        title = getString(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
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
        binding.edtConfirmPassword.addTextChangedListener { text ->
            text?.let {
                binding.tilConfirmPassword.error = null
                viewModel.updateConfirmPassword(text.toString())
            }
        }
    }

    private fun setupViewModelObserver() {
        viewModel.formErrors.observe(this, { formErrors ->
            formErrors?.let {
                formErrors.forEach { formError ->
                    when (formError) {
                        RegisterViewModel.FormErrors.INVALID_USER_NAME -> {
                            binding.tilUserName.error =
                                RegisterViewModel.FormErrors.INVALID_USER_NAME.value
                        }
                        RegisterViewModel.FormErrors.INVALID_PASSWORD -> {
                            binding.tilPassword.error =
                                RegisterViewModel.FormErrors.INVALID_PASSWORD.value
                        }
                        RegisterViewModel.FormErrors.INVALID_CONFIRM_PASSWORD -> {
                            binding.tilConfirmPassword.error =
                                RegisterViewModel.FormErrors.INVALID_CONFIRM_PASSWORD.value
                        }
                        RegisterViewModel.FormErrors.PASSWORD_NOT_MATCH -> {
                            binding.tilConfirmPassword.error =
                                RegisterViewModel.FormErrors.PASSWORD_NOT_MATCH.value
                        }
                    }
                }
            }
        })
    }

    private fun setupOnClickListener() {
        binding.btnRegister.setOnClickListener {
            onClickRegister()
        }
    }

    private fun setupViewModelFactory() {
        viewModelFactory = RegisterViewModel.Factory(LoginRepository())
    }

    private fun onClickRegister() {
        if (viewModel.validateForm()) {
            viewModel.register().observe(this, {
                when (it.status) {
                    BaseResult.Status.SUCCESS -> {
                        dismissProgressBar()
                        onRegisterSuccess(it)
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

    private fun onRegisterSuccess(result: BaseResult<RegisterResponse>) {
        result.data?.let {
            showSnackBar(MSG_REGISTER_SUCCESS)
            binding.edtUserName.text = null
            binding.edtPassword.text = null
            binding.edtConfirmPassword.text = null
        }
    }
}