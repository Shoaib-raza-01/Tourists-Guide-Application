package com.example.touristsguideapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CreateAccountpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_accountpage)

        val loginPage = findViewById<TextView>(R.id.Login)
        loginPage.setOnClickListener {
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }
    }
}