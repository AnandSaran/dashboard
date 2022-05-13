package com.work.dashboard.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.work.dashboard.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = getString(R.string.register)
    }
}