package com.example.godori.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_certif_tab_detail.*

class CertifTabDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_detail)

        // 데이터 베이스에서 Person마다 정보 가져오기


        backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}