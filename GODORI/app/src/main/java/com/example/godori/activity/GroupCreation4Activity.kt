package com.example.godori.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_group_creation4.*

class GroupCreation4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_creation4)

        // 주기 선택했는지 확인
        gr_rg_creation4_cycle.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {

            }
        }

        // 운동 선택하면 다음 버튼 활성화
        gr_cb_creation4_exercise1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gr_btn_creation4_next.isEnabled = true
            }
        }
        gr_cb_creation4_exercise2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gr_btn_creation4_next.isEnabled = true
            }
        }
        gr_cb_creation4_exercise3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gr_btn_creation4_next.isEnabled = true
            }
        }
        gr_cb_creation4_exercise4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gr_btn_creation4_next.isEnabled = true
            }
        }
        gr_cb_creation4_exercise5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gr_btn_creation4_next.isEnabled = true
            }
        }
        gr_cb_creation4_exercise6.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gr_btn_creation4_next.isEnabled = true
            }
        }

        // 다음 버튼
        gr_btn_creation4_next.setOnClickListener {
            val intent = Intent(this, GroupCreationCompleteActivity::class.java)
            startActivity(intent)
        }
    }
}