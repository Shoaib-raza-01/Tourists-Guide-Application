package com.example.touristsguideapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    //initializing firebase instance and google sign in client

    lateinit var googleSignInClient: GoogleSignInClient
    private val reqCode : Int = 123
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var signInBtn= findViewById<Button>(R.id.signIn)
        var googleSignInButton = findViewById<androidx.cardview.widget.CardView>(R.id.googleSignIn)
        var createUser= findViewById<TextView>(R.id.CreateAccount)

        FirebaseApp.initializeApp(this)

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(applicationContext,signInOptions)
        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInButton.setOnClickListener {
            Toast.makeText(applicationContext, " Google Sign In initiated ...." , Toast.LENGTH_SHORT).show()
            signInGoogle()
        }

        signInBtn.setOnClickListener{
            Toast.makeText(applicationContext,"Sign in sucessfully",Toast.LENGTH_LONG).show()
        }
        createUser.setOnClickListener{
            startActivity(Intent(applicationContext,CreateAccountpage::class.java))
        }
    }
    //this function will sign in the user
    private fun signInGoogle(){
        val signInIntent:Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,reqCode)

    }

    //now we provide the task and data for the google account in this onActivityResult function
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK && requestCode == reqCode){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }
    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account : GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if(account != null){
                updateScreen(account)
            }
        } catch (e: ApiException){
            Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    //now here we update the screen when the user is signed in successfully
    private  fun updateScreen(account : GoogleSignInAccount){
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credentials).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                SavedPreference.setEmail(this,account.email.toString())
                SavedPreference.setUsername(this, account.displayName.toString())
                val intent = Intent(this,Dashboard::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            startActivity(Intent(this,Dashboard::class.java))
            finish()
        }
    }
}