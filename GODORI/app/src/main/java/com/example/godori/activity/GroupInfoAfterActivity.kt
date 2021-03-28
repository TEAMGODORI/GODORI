package com.example.godori.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.godori.R
import com.example.godori.fragment.GroupInfo1Flagment
import com.example.godori.fragment.GroupInfo2Fragment
import kotlinx.android.synthetic.main.activity_group_info_after.*
import java.lang.Boolean.TRUE


class GroupInfoAfterActivity : AppCompatActivity() {
//    private val info1: View? = findViewById(R.id.gr_rl_info1)
//    private val info2: View? = findViewById(R.id.gr_rl_info2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info_after)

        // 그룹, 그룹원 정보 - FrameLayout
        supportFragmentManager.beginTransaction()
            .replace(R.id.gr_frame_info, GroupInfo1Flagment())
            .commit()
        gr_btn_info_1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.gr_frame_info, GroupInfo1Flagment())
                .commit()
        }
        gr_btn_info_2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.gr_frame_info, GroupInfo2Fragment())
                .commit()
        }

    }
}