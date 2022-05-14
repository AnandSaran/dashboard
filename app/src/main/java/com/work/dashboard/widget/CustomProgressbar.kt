package com.work.dashboard.widget

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.view.Window
import com.work.dashboard.R

class CustomProgressbar(context: Context) : Dialog(context) {
    init {
        createProgressBar()
    }

    private fun createProgressBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_bar)
        window?.let {
            it.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setBackgroundDrawableResource(android.R.color.transparent)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

    fun dismissProgress() {
        super.dismiss()
    }
}