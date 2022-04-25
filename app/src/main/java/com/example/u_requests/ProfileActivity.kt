package com.example.u_requests

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get

class ProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var OptionName: String
    private lateinit var DepartmentName: String
    private lateinit var ProfileName: String
    private lateinit var SchoolName: String
    private lateinit var adapter4: ArrayAdapter<CharSequence>
    private lateinit var adapter1: ArrayAdapter<CharSequence>
    private lateinit var adapter2: ArrayAdapter<CharSequence>
    private lateinit var adapter3: ArrayAdapter<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        //Options
        val spinnerOption: Spinner = findViewById(R.id.spinner_option)
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter4 = ArrayAdapter.createFromResource(
            this,
            R.array.options,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinnerOption.adapter = adapter4
        spinnerOption.onItemSelectedListener = this

        //Departments
        val spinnerDepartment: Spinner = findViewById(R.id.spinner_department)
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter3 = ArrayAdapter.createFromResource(
            this,
            R.array.departments,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinnerDepartment.adapter = adapter3
        spinnerDepartment.onItemSelectedListener = this

        //School
        val spinnerSchool: Spinner = findViewById(R.id.spinner_school)
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter1 = ArrayAdapter.createFromResource(
            this,
            R.array.schools,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinnerSchool.adapter = adapter1
        spinnerSchool.onItemSelectedListener = this

        //Profile
        val spinnerProfile: Spinner = findViewById(R.id.spinner_profile)
         // Create an ArrayAdapter using the string array and a default spinner layout
         adapter2 = ArrayAdapter.createFromResource(
             this,
             R.array.profile,
             android.R.layout.simple_spinner_item
         )
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinnerProfile.adapter = adapter2
        spinnerProfile.onItemSelectedListener = this



        val nextBtn = findViewById<Button>(R.id.button_profile_next)
        nextBtn.setOnClickListener {
            val schoolName = SchoolName
            val profileName = ProfileName
            val departmentName = DepartmentName
            val optionName = OptionName

            val cckb = findViewById<CheckBox>(R.id.checkBox_c)
            val cplusckb = findViewById<CheckBox>(R.id.checkBox_cplus)
            val csckb = findViewById<CheckBox>(R.id.checkBox_csharp)
            val javackb = findViewById<CheckBox>(R.id.checkBox_java)
            val androidckb = findViewById<CheckBox>(R.id.checkBox_android)
            val sickb = findViewById<CheckBox>(R.id.checkBox_si)
            val sgbdckb = findViewById<CheckBox>(R.id.checkBox_sgbd)
            val frckb = findViewById<CheckBox>(R.id.checkBox_fr)
            val enckb = findViewById<CheckBox>(R.id.checkBox_en)

            val corses = listOf<CheckBox>(cckb, cplusckb, csckb, javackb, androidckb, sickb, frckb, enckb, sgbdckb)

            UserInfo.School = schoolName
            UserInfo.Profile = profileName
            UserInfo.Department = departmentName
            UserInfo.Option = optionName

            if(profileName.lowercase() == "enseignant"){
                for (corse in corses){
                    if (corse.isChecked)
                        UserInfo.Corses.add(corse.text.toString())
                }
            }

            val intent = Intent(this, LoginModeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos)
        val linearCorses = findViewById<LinearLayout>(R.id.linearLayout_corses)
        val text = parent.getItemAtPosition(pos) as String
        if (text.lowercase() == "enseignant"){
            linearCorses.visibility = View.VISIBLE
        }
        else{
            linearCorses.visibility = View.GONE
        }
        when (parent.adapter) {
            adapter1 -> {
                SchoolName = text
            }
            adapter2 -> {
                ProfileName = text
            }
            adapter3 -> {
                DepartmentName = text
            }else->{
                OptionName = text
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        parent.getItemAtPosition(0)

        val text = parent.getItemAtPosition(0) as String
        when (parent.adapter) {
            adapter1 -> {
                SchoolName = text
            }
            adapter2 -> {
                ProfileName = text
            }
            adapter3 -> {
                DepartmentName = text
            }else->{
            OptionName = text
        }
        }
    }

}