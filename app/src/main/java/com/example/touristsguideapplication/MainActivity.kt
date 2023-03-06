package com.example.touristsguideapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var signInBtn= findViewById<Button>(R.id.signIn)
        var createUser= findViewById<TextView>(R.id.CreateAccount)
        signInBtn.setOnClickListener{
            Toast.makeText(applicationContext,"Sign in sucessfully",Toast.LENGTH_LONG).show()
        }
        createUser.setOnClickListener{
            startActivity(Intent(applicationContext,CreateAccountpage::class.java))
        }
    }
}