package com.example.u_requests

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class LoginModeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_mode)


        //Login mode
        val spinnerMode: Spinner = findViewById(R.id.spinner_mode)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.mode,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerMode.adapter = adapter
        }
        spinnerMode.onItemSelectedListener = this


        //next btn
        //val profileTxt = spinnerProfile.selectedItem.toString()
        val modeTxt = spinnerMode.selectedItem.toString()

        val nextBtn = findViewById<Button>(R.id.NextMode_btn)
        nextBtn.setOnClickListener {
            val modeTxt = spinnerMode.selectedItem.toString()

            if(modeTxt.lowercase() == "email"){
                val intent = Intent(this, EmailActivity1::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this, PhoneActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        parent.getItemAtPosition(0)
    }
}