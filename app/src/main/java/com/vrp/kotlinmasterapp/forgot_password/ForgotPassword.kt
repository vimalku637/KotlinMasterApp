package com.vrp.kotlinmasterapp.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.vrp.kotlinmasterapp.R
import com.vrp.kotlinmasterapp.login_activity.LoginActivity

class ForgotPassword : AppCompatActivity() {
    private val TAG = "ForgotPassword"
    //UI elements
    private var et_email: TextInputEditText? = null
    private var btn_submit: MaterialButton? = null
    //Firebase references
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Forgot Password"

        initialization()
        setButtonClick()
    }
    private fun setButtonClick() {
        btn_submit!!.setOnClickListener {
            sendPasswordResetEmail()
        }
    }

    private fun sendPasswordResetEmail() {
        val email = et_email?.text.toString()
        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val message = "Email sent."
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
                        Toast.makeText(this, "No user found with this email.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@ForgotPassword, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun initialization() {
        et_email = findViewById<View>(R.id.et_email) as TextInputEditText
        btn_submit = findViewById<View>(R.id.btn_submit) as MaterialButton
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val mIntent = Intent(this@ForgotPassword, LoginActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
    }
}
