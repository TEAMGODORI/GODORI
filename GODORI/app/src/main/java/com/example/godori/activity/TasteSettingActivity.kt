package com.example.godori.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_group_creation4.*
import kotlinx.android.synthetic.main.activity_group_creation4.gr_btn_creation4_next
import kotlinx.android.synthetic.main.activity_group_creation4.gr_cb_creation4_exercise1
import kotlinx.android.synthetic.main.activity_taste_setting.*

class TasteSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taste_setting)

        // 운동 선택하면 다음 버튼 활성화
        taste_btn_exercise1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                taste_btn_complete.isEnabled = true
            }
        }
        taste_btn_exercise2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                taste_btn_complete.isEnabled = true
            }
        }
        taste_btn_exercise3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                taste_btn_complete.isEnabled = true
            }
        }
        taste_btn_exercise4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                taste_btn_complete.isEnabled = true
            }
        }
        taste_btn_exercise5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                taste_btn_complete.isEnabled = true
            }
        }
        taste_btn_exercise6.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                taste_btn_complete.isEnabled = true
            }
        }
    }
}