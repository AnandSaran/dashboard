package com.work.dashboard.dashboard.view.activity

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.work.dashboard.R
import com.work.dashboard.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

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
        setAccountInfoCardViewCornerRadius()
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