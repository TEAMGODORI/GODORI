package com.example.godori.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_on_boarding2.*
import kotlinx.android.synthetic.main.activity_on_boarding2.btn2
import kotlinx.android.synthetic.main.activity_on_boarding3.*

class OnBoarding3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding3)

        btn3.setOnClickListener {
            val intent = Intent(this, TabBarActivity::class.java)
            startActivity(intent)
        }
    }
}