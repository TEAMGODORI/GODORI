package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.godori.fragment.CertifTabFragment
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_certif_tab_upload1.*
import kotlinx.android.synthetic.main.activity_certif_tab_upload3.*

class CertifTabUpload3Activity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_upload3)

        backBtn3.setOnClickListener {
            //백버튼 눌렀을 때
            onBackPressed()
        }

        next3Btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CertifTabUpload4Activity::class.java)
            startActivity(intent)
        })
    }

}

