package com.work.dashboard.login.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.work.dashboard.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = getString(R.string.login)
    }
}