package com.example.u_requests

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)

        val buttonNext = findViewById<Button>(R.id.userInfo_next_btn)
        val firstName = findViewById<EditText>(R.id.user_first_name_Et)
        val lastName = findViewById<EditText>(R.id.user_last_name_Et)
        val password = findViewById<EditText>(R.id.user_password_Et)
        val confPassword = findViewById<EditText>(R.id.user_confirmPassword_Et)

        buttonNext.setOnClickListener {
            val fname = firstName.text.toString()
            val lname = lastName.text.toString()
            val pass = password.text.toString()
            val cPass = confPassword.text.toString()
            if(fname.isNullOrEmpty() || lname.isNullOrEmpty() || pass.isNullOrEmpty() || cPass.isNullOrEmpty()){
                firstName.error = "This field must not be empty"
                lastName.error = "This field must not be empty"
                password.error = "This field must not be empty"
                confPassword.error = "This field must not be empty"
            }
            else if(pass != cPass)
                confPassword.error = "Not matching password"
            else{
                UserInfo.First_name = fname
                UserInfo.Last_name = lname
                UserInfo.Password = pass
                MyDataBaseHelper(this).updateData("1", "2- $fname $lname registered")

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }

        }
    }
}