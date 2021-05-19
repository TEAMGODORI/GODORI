package com.example.godori.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_on_boarding1.*

class OnBoarding1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding1)

        btn1.setOnClickListener {
            val intent = Intent(this, OnBoarding2Activity::class.java)
            startActivity(intent)
        }

        ob_btn_back1.setOnClickListener {
            onBackPressed()
        }
    }
}