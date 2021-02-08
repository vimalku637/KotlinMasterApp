package com.vrp.kotlinmasterapp.login_activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.vrp.kotlinmasterapp.MainActivity
import com.vrp.kotlinmasterapp.R
import com.vrp.kotlinmasterapp.forgot_password.ForgotPassword
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    //UI elements
    private var et_user_name: TextInputEditText? = null
    private var et_password: TextInputEditText? = null
    private var tv_forgot_password: MaterialTextView? = null
    private var btn_submit: MaterialButton? = null
    private var mProgressBar: ProgressDialog? = null
    //global variables
    private var user_name: String? = null
    private var user_password: String? = null
    //Firebase references
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Sign In"

        initialization()
        setSubmitClick()
    }

    private fun initialization() {
        et_user_name = findViewById<View>(R.id.et_user_name) as TextInputEditText
        et_password = findViewById<View>(R.id.et_password) as TextInputEditText
        tv_forgot_password = findViewById<View>(R.id.tv_forgot_password) as MaterialTextView
        btn_submit = findViewById<View>(R.id.btn_submit) as MaterialButton

        mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun setSubmitClick() {
        btn_submit!!.setOnClickListener {
           signIn()
        }
        tv_forgot_password!!.setOnClickListener {
            val mIntent = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(mIntent)
        }
    }

    private fun signIn() {
        user_name =  et_user_name?.text.toString().trim()
        user_password =  et_password?.text.toString().trim()

        if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_password)) {
            Toast.makeText(this, user_name +"\n"+ user_password, Toast.LENGTH_LONG).show()
            mProgressBar!!.setMessage("Registering User...")
            mProgressBar!!.show()
            Log.d(TAG, "Logging in user.")
            mAuth!!.signInWithEmailAndPassword(user_name!!, user_password!!)
                .addOnCompleteListener(this) { task ->
                    mProgressBar!!.hide()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please enter user name and password! ", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {
        val mIntent = Intent(this@LoginActivity, MainActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val mIntent = Intent(this@LoginActivity, MainActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
        finish()
    }
}
