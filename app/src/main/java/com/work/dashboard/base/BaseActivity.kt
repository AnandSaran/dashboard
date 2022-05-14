package com.work.dashboard.base

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.work.dashboard.widget.CustomProgressbar

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var customProgressbar: CustomProgressbar
    protected val TAG = this.javaClass.simpleName

    fun showProgressBar() {
        getProgressBar().show()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun getProgressBar(): CustomProgressbar {
        if (!::customProgressbar.isInitialized) {
            customProgressbar = CustomProgressbar(this)
        }
        return customProgressbar
    }

    fun dismissProgressBar() {
        runOnUiThread {
            try {
                getProgressBar().dismissProgress()
            } catch (e: Exception) {

            }
        }
    }
}