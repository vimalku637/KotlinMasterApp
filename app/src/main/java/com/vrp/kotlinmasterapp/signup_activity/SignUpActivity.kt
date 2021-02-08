package com.vrp.kotlinmasterapp.signup_activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.vrp.kotlinmasterapp.MainActivity
import com.vrp.kotlinmasterapp.R
import com.vrp.kotlinmasterapp.utils.SweetAlertDialog

class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"
    //UI elements
    private var et_first_name: TextInputEditText? = null
    private var et_last_name: TextInputEditText? = null
    private var et_email: TextInputEditText? = null
    private var et_password: TextInputEditText? = null
    private var btn_submit: MaterialButton? = null
    //private var mProgressBar: ProgressDialog? = null
    private var sweetAlertDialog = SweetAlertDialog()
    //Firebase References
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    //global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Sign Up"

        initialization()
        setButtonClick()
    }

    private fun setButtonClick() {
        btn_submit!!.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        firstName = et_first_name?.text.toString().trim()
        lastName = et_last_name?.text.toString().trim()
        email = et_email?.text.toString().trim()
        password = et_password?.text.toString().trim()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Toast.makeText(this, firstName +"\n"+ lastName +"\n"+ email +"\n"+ password, Toast.LENGTH_LONG).show()

            /*mProgressBar!!.setMessage("Please Wait...")
            mProgressBar!!.show()*/
            sweetAlertDialog.showProgressDialog(this)

            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    sweetAlertDialog!!.dismissProgressDialog()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val userId = mAuth!!.currentUser!!.uid

                        //Verify Email
                        verifyEmail()

                        //update user profile information
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("firstName").setValue(firstName)
                        currentUserDb.child("lastName").setValue(lastName)

                        updateUserInfoAndUI()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@SignUpActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            Toast.makeText(this, "Enter all details.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val mIntent = Intent(this@SignUpActivity, MainActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this@SignUpActivity,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_LONG).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@SignUpActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_LONG).show()
                }
            }    }

    private fun initialization() {
        et_first_name = findViewById<View>(R.id.et_first_name) as TextInputEditText
        et_last_name = findViewById<View>(R.id.et_last_name) as TextInputEditText
        et_email = findViewById<View>(R.id.et_email) as TextInputEditText
        et_password = findViewById<View>(R.id.et_password) as TextInputEditText
        btn_submit = findViewById<View>(R.id.btn_submit) as MaterialButton

        //mProgressBar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val mIntent = Intent(this@SignUpActivity, MainActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
        finish()
    }
}
