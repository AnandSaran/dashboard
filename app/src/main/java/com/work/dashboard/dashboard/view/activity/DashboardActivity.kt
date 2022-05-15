package com.work.dashboard.dashboard.view.activity

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.work.dashboard.R
import com.work.dashboard.adapter.DayTransactionAdapter
import com.work.dashboard.base.BaseActivity
import com.work.dashboard.base.BaseResult
import com.work.dashboard.dashboard.view.viewmodel.DashboardViewModel
import com.work.dashboard.databinding.ActivityDashboardBinding
import com.work.dashboard.network.repository.AccountRepository
import com.work.dashboard.network.resposne.Data
import com.work.dashboard.transfer.activity.TransferActivity
import com.work.dashboard.util.constants.*

class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModelFactory: DashboardViewModel.Factory
    private val viewModel: DashboardViewModel by lazy {
        requireNotNull(this) {
            VIEW_MODEL_IN_ACCESSIBLE_MESSAGE
        }
        ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
    }
    private val userToken: String by lazy {
        intent.getStringExtra(BUNDLE_KEY_TOKEN) ?: EMPTY_STRING
    }
    private val userName: String by lazy {
        intent.getStringExtra(BUNDLE_KEY_USER_NAME) ?: EMPTY_STRING
    }

    private val userAccountNo: String by lazy {
        intent.getStringExtra(BUNDLE_KEY_USER_ACCOUNT_NO) ?: EMPTY_STRING
    }

    companion object {
        fun present(context: Context, bundle: Bundle) {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.dashboard)
        setupOnClickListener()
        setupViewModelFactory()
        setAccountInfoCardViewCornerRadius()
        setUserName()
        setUserAccountNumber()
        fetchBalance()
        fetchTransAction()
    }

    private fun setupOnClickListener() {
        binding.btnMakeTransfer.setOnClickListener {
            onClickMakeTransfer()
        }
    }

    private fun onClickMakeTransfer() {
        TransferActivity.present(this)
    }

    private fun fetchBalance() {
        viewModel.fetchBalance(userToken).observe(this, {
            when (it.status) {
                BaseResult.Status.SUCCESS -> {
                    handleBalanceResponse(it.data)
                }
                BaseResult.Status.ERROR -> {
                    showSnackBar(it.message)
                }
                BaseResult.Status.LOADING -> {
                }
            }
        })
    }


    private fun fetchTransAction() {
        viewModel.fetchTransAction(userToken).observe(this, {
            when (it.status) {
                BaseResult.Status.SUCCESS -> {
                    it?.let {
                        onFetchTransactionSuccess(it)
                    }
                }
                BaseResult.Status.ERROR -> {
                    showSnackBar(it.message)
                }
                BaseResult.Status.LOADING -> {
                }
            }
        })
    }

    private fun onFetchTransactionSuccess(result: BaseResult<Map<String, List<Data>>>) {
        result.data?.let {
            setupTransactionList(it)
        }

    }

    private fun setupTransactionList(map: Map<String, List<Data>>) {
        binding.rvTransactions.apply {
            adapter = DayTransactionAdapter(map)
        }
    }

    private fun handleBalanceResponse(balance: String?) {
        balance?.let {
            setAccountBalance(balance)
        }
    }

    private fun setupViewModelFactory() {
        viewModelFactory = DashboardViewModel.Factory(AccountRepository())
    }

    private fun setUserName() {
        binding.tvAccountHolderName.text = userName
    }

    private fun setUserAccountNumber() {
        binding.tvAccountNumber.text = userAccountNo
    }

    private fun setAccountBalance(balance: String) {
        binding.tvBalance.text = balance
    }

    private fun setAccountInfoCardViewCornerRadius() {
        val leftShapePathModel = ShapeAppearanceModel().toBuilder()
        leftShapePathModel.setTopRightCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 80F })
        leftShapePathModel.setBottomRightCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 80F })

        val bg = MaterialShapeDrawable(leftShapePathModel.build())
        bg.fillColor = ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.white)
        )
        bg.elevation = 8F
        binding.cvAccountInfo.background = bg
        binding.cvAccountInfo.invalidate()
    }
}