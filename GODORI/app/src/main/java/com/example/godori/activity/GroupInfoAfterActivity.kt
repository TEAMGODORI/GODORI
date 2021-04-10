package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.adapter.TabBarViewPagerAdapter
import com.example.godori.data.ResponseGroupAfterTab
import com.example.godori.data.ResponseGroupCreationData
import com.example.godori.fragment.GroupInfo1Flagment
import com.example.godori.fragment.GroupInfo2Fragment
import kotlinx.android.synthetic.main.activity_group_info_after.*
import kotlinx.android.synthetic.main.activity_tab_bar.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Boolean.TRUE


class GroupInfoAfterActivity : AppCompatActivity() {

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

        // 다른 그룹 구경가기
        gr_btn_otherGroup.setOnClickListener {
            val intent = Intent(this, GroupRecruitingActivity::class.java)
            startActivity(intent)
        }

        // 그룹 탈퇴하기
        gr_btn_exit.setOnClickListener {
            // 1. 그룹 탈퇴 api 호출
            val call: Call<ResponseGroupCreationData> =
                GroupRetrofitServiceImpl.service_gr_exit.requestList(
                    userName = "테스터3" // 수정하기
                )
            call.enqueue(object : Callback<ResponseGroupCreationData> {
                override fun onFailure(call: Call<ResponseGroupCreationData>, t: Throwable) {
                    // 통신 실패 로직
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<ResponseGroupCreationData>,
                    response: Response<ResponseGroupCreationData>
                ) {
                    response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let { it ->
                            // do something

                        } ?: showError(response.errorBody())
                }
            })

            // 2. 가입전 메인 화면으로
            val intent = Intent(application, TabBarActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }

    // api 호출 에러 함수
    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
    }
}