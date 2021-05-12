package com.example.godori.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_on_boarding1.*
import kotlinx.android.synthetic.main.activity_on_boarding1.btn1
import kotlinx.android.synthetic.main.activity_on_boarding2.*

class OnBoarding2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding2)

        btn2.setOnClickListener {
            val intent = Intent(this, OnBoarding3Activity::class.java)
            startActivity(intent)
        }
    }
}