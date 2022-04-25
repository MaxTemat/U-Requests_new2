package com.example.u_requests

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddRequestActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var TheSession: String = ""
    private var TheExam: String = ""
    private lateinit var adapter1: ArrayAdapter<CharSequence>
    private lateinit var adapter2: ArrayAdapter<CharSequence>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request)

        val spinnerExam: Spinner = findViewById(R.id.spinner_exam_addRequest)
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter1 = ArrayAdapter.createFromResource(
            this,
            R.array.exams,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinnerExam.adapter = adapter1
        spinnerExam.onItemSelectedListener = this

        val spinnerSession: Spinner = findViewById(R.id.spinner_session_addRequest)
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.sessions,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinnerSession.adapter = adapter2
        spinnerSession.onItemSelectedListener = this


        val theObject = findViewById<EditText>(R.id.object_Et_addRequest)
        val password = findViewById<EditText>(R.id.password_Et_addRequest)
        val theSubject = findViewById<EditText>(R.id.subject_Et_addRequest)
        val sendBtn = findViewById<Button>(R.id.sendBtn_addRequest)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn_addRequest)

        sendBtn.setOnClickListener {
            if(theObject.text.toString().isNullOrEmpty() || theSubject.text.toString().isNullOrEmpty()
                || password.text.toString().isNullOrEmpty()){
                theObject.error = "This field must not be empty"
                theSubject.error = "This field must not be empty"
                password.error = "This field must not be empty"
            }
            else{
                val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                RequestInfo.CreatedAt = LocalDateTime.now().format(format)
                RequestInfo.Object = theObject.text.toString()
                RequestInfo.Subject =theSubject.text.toString()
                RequestInfo.OwnerPassword = password.text.toString()
                RequestInfo.Exam = TheExam
                RequestInfo.Session = TheSession

                finish()
            }
        }
        cancelBtn.setOnClickListener {
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos)

        val text = parent.getItemAtPosition(pos) as String
        when (parent.adapter) {
            adapter1 -> {
                TheExam = text
            }else->{
                TheSession = text
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        parent.getItemAtPosition(0)

        val text = parent.getItemAtPosition(0) as String
        when (parent.adapter) {
            adapter1 -> {
                TheExam = text
            }else->{
                TheSession = text
            }
        }
    }
}