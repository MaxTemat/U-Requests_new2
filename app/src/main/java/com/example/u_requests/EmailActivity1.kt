package com.example.u_requests

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.io.*

open class EmailActivity1 : AppCompatActivity() {
    private var emailEt: EditText? = null
    private var passEt: EditText? = null
    private var signUpBtn: Button? = null
    private var progressDialog: ProgressDialog? = null
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        firebaseAuth = FirebaseAuth.getInstance()
        emailEt = findViewById(R.id.email_mail_Et)
        passEt = findViewById(R.id.email_passwd_Et)
        val phoneTv = findViewById<TextView>(R.id.emailPhone_link)
        val googleBtn = findViewById<ImageButton>(R.id.emailGoogle_btn)
        signUpBtn = findViewById(R.id.emailNext_btn)
        progressDialog = ProgressDialog(this)
        signUpBtn!!.setOnClickListener {
            register()
        }
        phoneTv.setOnClickListener {
            val intent = Intent(this, PhoneActivity::class.java)
            startActivity(intent)
            finish()
        }
        googleBtn.setOnClickListener{
            val intent = Intent(this, GoogleLoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register() {
        val emailTv = findViewById<EditText>(R.id.email_mail_Et)
        val passwordTv = findViewById<EditText>(R.id.email_passwd_Et)
        val confirmPasswordTv = findViewById<EditText>(R.id.confirm_pass_Et)
        val email = emailTv.text.toString()
        val password = passwordTv.text.toString()
        val cPassword = confirmPasswordTv.text.toString()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            emailTv.error = "This field must not be empty !"
            passwordTv.error = "This field must not be empty !"
            return
        }
        if(!email.contains("@") || !email.contains(".com")){
            emailTv.error = "Bad email format !"
            return
        }
        if(password != cPassword){
            confirmPasswordTv.error = "Not matching password !"
            return
        }
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.show()
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful)
                {
                    progressDialog!!.dismiss()

                    MyDataBaseHelper(this).addData("1- ${firebaseAuth?.currentUser?.phoneNumber}")
                    UserInfo.Email = email
                    val intent = Intent(this, UserInfoActivity::class.java)
                    startActivity(intent)
                    finishActivity(200)
                    finish()
                }
                else
                {
                    progressDialog!!.dismiss()
                    Toast.makeText(this, "${task.exception}", Toast.LENGTH_LONG).show()
                }

            }
    }
}