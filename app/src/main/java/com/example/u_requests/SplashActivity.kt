package com.example.u_requests

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                val intentProfile = Intent(this, ProfileActivity::class.java)
                val intentUserInfo = Intent(this, UserInfoActivity::class.java)
                val intentHome = Intent(this, HomeActivity::class.java)

                val cursor = MyDataBaseHelper(this).readAllData()

                var data = ""
                while (cursor?.moveToNext() == true)
                    data = cursor?.getString(1).toString()

                if (!data.isNullOrEmpty()){
                    if (data.startsWith('2'))
                        startActivity(intentHome)
                    else if(data.startsWith('1'))
                        startActivity(intentUserInfo)
                }
                else
                    startActivity(intentProfile)
                finish()
            },
            3000
        )
    }
}