package com.example.coronavirusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val mEmail = findViewById<EditText>(R.id.email)
        val mPassword = findViewById<EditText>(R.id.setPassword)
        val mLoginBtn = findViewById<Button>(R.id.loginButton)
        val mRegisterBtn = findViewById<TextView>(R.id.textView3)
        val forgotTextLink = findViewById<TextView>(R.id.forgotPassword)
        val fAuth = FirebaseAuth.getInstance()


        mRegisterBtn.setOnClickListener { startActivity(Intent(applicationContext, Register::class.java)) }

        mLoginBtn.setOnClickListener(object: View.OnClickListener {
            @Override
            override fun onClick(v: View) {
                val email = mEmail.text.toString().trim()
                val password = mPassword.text.toString().trim()

                if (TextUtils.isEmpty(email)) {
                    mEmail.error = "Email is Required"
                    return
                }

                if (password.length < 6) {
                    mPassword.error = "Password must be at least 6 characters"
                    return
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this@Login, "Logged in Successfully.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(this@Login, "Error! " + task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


        /*forgotTextLink.setOnClickListener(object: View.OnClickListener {
            @Override
            override fun onClick(v: View) {
                EditText resetMail
            }
        })*/

    }
}
