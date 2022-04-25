package com.example.u_requests

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.io.*
import java.util.concurrent.TimeUnit


class PhoneActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var hasClicked = false
    private lateinit var codeEt: EditText
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var authPhone: FirebaseAuth? = null
    private lateinit var authMail: FirebaseAuth
    private var progressDialog: ProgressDialog? = null
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private val listCode = listOf<String>("+93","+355","+376","+23","+12","+45","+34","+33","+49","+62","+237")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        //timer
        val mTextField = findViewById<TextView>(R.id.view_timer_Tv)
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTextField.text = "00: " + millisUntilFinished / 1000
            }

            override fun onFinish() {
                mTextField.text = "00:00"
            }
        }.start()

        codeEt = findViewById(R.id.country_code_Et)
        val phoneLy = findViewById<LinearLayout>(R.id.enter_phone_Ly)
        val codeLy = findViewById<LinearLayout>(R.id.enter_code_Ly)
        codeLy.visibility = View.GONE
        phoneLy.visibility = View.VISIBLE
        val codeSentEt = findViewById<EditText>(R.id.code_sent_Et)
        val spinnerCountry: Spinner = findViewById(R.id.spinner_country)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.countries,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCountry.adapter = adapter
        }
        spinnerCountry.onItemSelectedListener = this

        codeEt!!.setOnFocusChangeListener { _, _ ->
            val text = codeEt!!.text.toString()
            if (listCode.contains(text)){
                val p = listCode.indexOf(text)
                spinnerCountry.setSelection(p)
            }
        }

        val changeTv = findViewById<TextView>(R.id.change_number_Tv)
        changeTv.setOnClickListener {
            hasClicked = false
            codeLy.visibility = View.GONE
            phoneLy.visibility = View.VISIBLE
            val verifyTv = findViewById<TextView>(R.id.verify_number_Tv)
            val messageTv = findViewById<TextView>(R.id.message_verify_Tv)
            verifyTv.text = "Verify your phone number"
            messageTv.text = "URequests will send an SMS to verify your phone number." +
                    " Enter your country code and phone number"
        }

        val emailTv = findViewById<TextView>(R.id.phone_email_link)
        emailTv.setOnClickListener {
            val intent = Intent(this, EmailActivity1::class.java)
            startActivity(intent)
            finish()
        }



        val phoneNumberEt = findViewById<EditText>(R.id.phone_number_Et)
        val code = findViewById<EditText>(R.id.country_code_Et)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCanceledOnTouchOutside(false)
        authPhone = FirebaseAuth.getInstance()
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                progressDialog!!.dismiss()
                Toast.makeText(this@PhoneActivity, credential.smsCode, Toast.LENGTH_LONG).show()
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(ContentValues.TAG, "onVerificationFailed", e)
                progressDialog!!.dismiss()
                Toast.makeText(this@PhoneActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(this@PhoneActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(this@PhoneActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(ContentValues.TAG, "onCodeSent:$verificationId")
                progressDialog!!.dismiss()
                Toast.makeText(this@PhoneActivity, "code: $token", Toast.LENGTH_SHORT).show()

                // Save verification ID and resending token so we can use them later
                //val credential = PhoneAuthProvider.getCredential(storedVerificationId, token.toString())
                //signInWithPhoneAuthCredential(credential)
                storedVerificationId = verificationId
                resendToken = token
            }
        }
        val nextBtn = findViewById<Button>(R.id.nextPhone_btn)
        nextBtn.setOnClickListener {
            val codeTxt = code.text.toString()
            val phoneTxt = phoneNumberEt.text.toString()
            UserInfo.Phone = phoneTxt
            if(codeTxt.isNullOrEmpty()){
                phoneNumberEt.error = "This field must not be empty"
            }
            else if( phoneTxt.isNullOrEmpty()){
                phoneNumberEt.error = "This field must not be empty"
            }
            else if (phoneTxt.length != 9)
            {
                phoneNumberEt.error = "Bad format phone number"
            }
            else if(!hasClicked){

                hasClicked = true
                phoneLy.visibility = View.GONE
                codeLy.visibility = View.VISIBLE
                val verifyTv = findViewById<TextView>(R.id.verify_number_Tv)
                val messageTv = findViewById<TextView>(R.id.message_verify_Tv)
                verifyTv.text = "Verifying your number"
                messageTv.text = "Waiting to automatically detect an SMS sent to your phone number"
                progressDialog!!.setTitle("Verifying...")
                progressDialog!!.show()
                val phoneNumber = code.text.toString() + phoneNumberEt.text.toString()
                val options = PhoneAuthOptions.newBuilder(authPhone!!)
                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks!!)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
            else{
                val codeSentTxt = codeSentEt.text.toString()
                if(codeSentTxt.isNullOrEmpty()){
                    phoneNumberEt.error = "This field must not be empty"
                }
                val verificationCode = codeSentTxt
                val credential = PhoneAuthProvider.getCredential(storedVerificationId, verificationCode)
                signInWithPhoneAuthCredential(credential)
            }
        }
    }
    fun String.toEditable(): Editable{
        return Editable.Factory.getInstance().newEditable(this)
    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos)
        codeEt!!.text = listCode[pos].toEditable()
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        parent.getItemAtPosition(0)
        codeEt!!.text = listCode[0].toEditable()
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog!!.setTitle("Verifying...")
        progressDialog!!.show()
        authPhone!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    progressDialog!!.dismiss()
                    MyDataBaseHelper(this).addData("1- ${authPhone?.currentUser?.phoneNumber}")

                    val intent = Intent(this, UserInfoActivity::class.java)
                    startActivity(intent)
                    finishActivity(200)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    progressDialog!!.dismiss()
                    Toast.makeText(this, "${task.exception!!.message}", Toast.LENGTH_SHORT).show()
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}