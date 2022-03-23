package com.biswa1045.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var signup: Button
    private lateinit var signin: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var mref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email_signup)
        name = findViewById(R.id.name_signup)
        password = findViewById(R.id.password_signup)
        signin = findViewById(R.id.signin_text)
        signup = findViewById(R.id.signup_btn)

        signin.setOnClickListener{
            val intent2 = Intent(this@SignUpActivity,MainActivity::class.java)
            finish()
            startActivity(intent2)
        }
        signup.setOnClickListener{
            val email_txt = email.text.toString()
            val password_txt = password.text.toString()
            val name_txt = name.text.toString()
            if(!name_txt.equals("") && !password_txt.equals("")&& !name_txt.equals("")){

                sign_up(email_txt,password_txt,name_txt)
            }else{
                Toast.makeText(baseContext, "Fill all Field",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun sign_up(email:String,password:String,name:String){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@SignUpActivity,HomeActivity::class.java)
                    startActivity(intent)
                    create_database(name,email,auth.currentUser?.uid!!)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
        signup.setEnabled(false)
    }
    fun create_database(name:String,email:String,uid:String){
        mref = FirebaseDatabase.getInstance().getReference()
        mref.child("user").child(uid).setValue(User(name, email, uid))

    }
}