package com.vrp.kotlinmasterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vrp.kotlinmasterapp.login_activity.LoginActivity
import com.vrp.kotlinmasterapp.signup_activity.SignUpActivity
import com.vrp.kotlinmasterapp.walkthrough.WalkThrough
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Home Activity"

        setAllButtonClick()
    }

    private fun setAllButtonClick() {
        tv_app_intro!!.setOnClickListener {
            Toast.makeText(this, getString(R.string.welcome_to_kotlin_programming), Toast.LENGTH_SHORT).show()
        }
        tv_login_activity!!.setOnClickListener {
            val mIntent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(mIntent)
        }
        tv_signup_activity!!.setOnClickListener {
            val mIntent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(mIntent)
        }
    }
}
