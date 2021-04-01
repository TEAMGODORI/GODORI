package com.example.godori.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_group_creation_complete.*

class GroupCreationCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_creation_complete)

        // 데이터 가져오기
        val secondIntent = getIntent()
        var group_name = secondIntent.getStringExtra("group_name").toString()
        var recruit_num = secondIntent.getIntExtra("recruit_num",0)
        var intro_comment = secondIntent.getStringExtra("intro_comment").toString()
        var ex_intensity = secondIntent.getStringExtra("ex_intensity").toString()
        var ex_cycle = secondIntent.getIntExtra("ex_cycle", 0)

        // 텍스트뷰 셋팅
        gr_tv_creation_groupName.text = group_name
        gr_tv_creation_introComment.text = intro_comment
        gr_tv_creation_cycle.text = "주${ex_cycle}회"
        gr_tv_creation_intensity.text = ex_intensity
        gr_tv_creation_num.text = "${recruit_num}명"
    }
}