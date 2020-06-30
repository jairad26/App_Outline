package com.example.coronavirusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val mFullName = findViewById<EditText>(R.id.fullName)
        val mEmail = findViewById<EditText>(R.id.email)
        val mPassword = findViewById<EditText>(R.id.setPassword)
        val mPhone = findViewById<EditText>(R.id.phoneNumber)
        val mRegisterBtn = findViewById<Button>(R.id.registerButton)
        val mLoginBtn = findViewById<TextView>(R.id.createText)
        val mZipcode = findViewById<EditText>(R.id.zipcode)

        val fAuth = FirebaseAuth.getInstance()
        val fStore = FirebaseFirestore.getInstance()

        if(fAuth.currentUser != null)
        {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }


        mLoginBtn.setOnClickListener { startActivity(Intent(applicationContext, Login::class.java)) }


        mRegisterBtn.setOnClickListener(object: View.OnClickListener
        {
            @Override
            override fun onClick(v: View)
            {
                val email = mEmail.text.toString().trim()
                val password = mPassword.text.toString().trim()
                val fullName = mFullName.text.toString().trim()
                val phoneNumber = mPhone.text.toString().trim()
                val zipcode = mZipcode.text.toString().trim()

                if(TextUtils.isEmpty(fullName))
                {
                    mFullName.error = "Full Name is Required"
                    return
                }
                if(TextUtils.isEmpty(email))
                {
                    mEmail.error = "Email is Required"
                    return
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.error = "Password is Required"
                    return
                }
                if(TextUtils.isEmpty(phoneNumber))
                {
                    mPhone.error = "Phone is Required"
                    return
                }
                if(TextUtils.isEmpty(zipcode))
                {
                    mZipcode.error = "Zipcode is Required"
                    return
                }
                if(password.length < 6)
                {
                    mPassword.error = "Password must be at least 6 characters"
                    return
                }
                if(phoneNumber.length < 10)
                {
                    mPhone.error = "Phone Number must be at least 10 characters"
                    return
                }
                if(zipcode.length < 5)
                {
                    mZipcode.error = "Zipcode must be at least 5 characters"
                    return
                }

                //register user to firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this@Register, "User Created.", Toast.LENGTH_SHORT).show()
                        val userID = fAuth.currentUser?.uid
                        val documentReference = userID?.let {
                            fStore.collection("users").document(
                                it
                            )
                        }
                        val user = HashMap<String, String>()
                        user["fName"] = fullName
                        user["email"] = email
                        user["phone"] = phoneNumber
                        user["zipcode"] = zipcode
                        documentReference?.set(user)?.addOnSuccessListener { Log.d("TAG", "on Success: user profile created for $userID") }
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(this@Register, "Error! " + task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
