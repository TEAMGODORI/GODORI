package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.data.ResponseGroupCreationData
import com.example.godori.data.ResponseGroupInfoAfter
import com.example.godori.fragment.GroupInfo1Flagment
import com.example.godori.fragment.GroupInfo2Fragment
import kotlinx.android.synthetic.main.activity_group_info_after.*
import kotlinx.android.synthetic.main.fragment_group_info1.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GroupInfoAfterActivity : AppCompatActivity() {
    var data : ResponseGroupInfoAfter? = null
    var groupData : ResponseGroupInfoAfter.Data.GroupDetail? = null
    var memberList: List<ResponseGroupInfoAfter.Data.GroupMember>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info_after)

        val secondIntent = intent
        var groupId = secondIntent.extras?.getInt("groupId")

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
                    userName = "김지현" // 수정하기
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


        //group_id로 그룹 정보 가져오기
        val call: Call<ResponseGroupInfoAfter> =
            GroupRetrofitServiceImpl.service_gr__info_after.requestList(
                groupId = 29//수정하기
            )
        call.enqueue(object : Callback<ResponseGroupInfoAfter> {
            override fun onFailure(call: Call<ResponseGroupInfoAfter>, t: Throwable) {
                // 통신 실패 로직
            }

            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onResponse(
                call: Call<ResponseGroupInfoAfter>,
                response: Response<ResponseGroupInfoAfter>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        // do something
                        data = response.body()
                        groupData = data!!.data.group_detail
                        Log.d("GroupRecruitingActivity", groupData.toString())
                        memberList = data!!.data.group_member
                        Log.d("GroupRecruitingActivity", memberList.toString())

                        gr_tv_info_after_title_exercise.setText(groupData!!.group_sport)
                        gr_tv_info_after_title_name.setText(groupData!!.group_name)
                        gr_tv_info_after_title_line.setText(groupData!!.intro_comment)
//                        val dateFormat: DateFormat = SimpleDateFormat("YYYY.MM.dd")
//                        val date = dateFormat.format(dataD!!.created_at)
//                        //created_at
//                        gr_tv_info_after_title_startdate.setText()
                        gr_tv_info_after_group_maker.setText(groupData!!.group_maker)

                        //GroupInfo1Fragment
                        //recruited_num
                        gr_tv_info_after_member_recruit_num.setText(groupData!!.recruited_num.toString() + "/")
                        //recruit_num
                        gr_tv_info_after_member_total_num.setText(groupData!!.recruit_num.toString())
                        //ex_cycle
                        gr_tv_info_after_cycle_num.setText("주" + groupData!!.ex_cycle.toString() + "회")
                        //ex_intensity
                        gr_tv_info_after_level_num.setText(groupData!!.ex_intensity)
                        //achive_rate
                        gr_tv_info_after_percent_num.setText(groupData!!.achive_rate.toString() + "%")
                        //group_sport
                        gr_tv_info_after_exercise_num.setText(groupData!!.group_sport)

                        //GroupInfo2Fragment


                    } ?: showError(response.errorBody())
            }
        })

    }

    // api 호출 에러 함수
    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
    }
}