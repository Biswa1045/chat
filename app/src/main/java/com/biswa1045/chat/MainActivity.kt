package com.biswa1045.chat

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signin: Button
    private lateinit var withgoogle: Button
    private lateinit var signup: TextView
    private lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email_signin)
        password = findViewById(R.id.password_signin)
        signin = findViewById(R.id.signin_btn)
        signup = findViewById(R.id.signup_text)
        withgoogle = findViewById(R.id.signin_with_google)
        signin.setOnClickListener{
            val email_txt = email.text.toString()
            val password_txt = password.text.toString()
            if(!email_txt.equals("") && !password_txt.equals("")){

                sign_in(email_txt,password_txt);
            }else{
                Toast.makeText(baseContext, "Fill all Field",
                    Toast.LENGTH_SHORT).show()
            }

        }
        signup.setOnClickListener{
            val intent2 = Intent(this@MainActivity,SignUpActivity::class.java)
            finish()
            startActivity(intent2)
        }

    }
    fun sign_in(email:String,password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@MainActivity,HomeActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
        signin.setEnabled(false)
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@MainActivity,HomeActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

}