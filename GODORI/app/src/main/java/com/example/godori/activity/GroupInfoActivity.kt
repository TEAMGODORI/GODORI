package com.example.godori.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.data.ResponseGroupInfo
import kotlinx.android.synthetic.main.activity_group_info.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupInfoActivity : AppCompatActivity() {

    var data : ResponseGroupInfo? = null
    var dataD : ResponseGroupInfo.Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)

        var group_id : Int
        val extras = intent.extras
        group_id = extras!!.getInt("groupId")

        //group_id로 그룹 정보 가져오기
        val call: Call<ResponseGroupInfo> =
            GroupRetrofitServiceImpl.service_gr__info.requestList(
                groupId = group_id //수정하기
            )
        call.enqueue(object : Callback<ResponseGroupInfo> {
            override fun onFailure(call: Call<ResponseGroupInfo>, t: Throwable) {
                // 통신 실패 로직
            }

            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onResponse(
                call: Call<ResponseGroupInfo>,
                response: Response<ResponseGroupInfo>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        // do something
                        data = response.body()
                        Log.d("GroupRecruitingActivity", data.toString())
                        dataD = data!!.data
                        Log.d("GroupRecruitingActivity", dataD.toString())

                        //group_sport
                        gr_tv_info_title_exercise.setText(dataD!!.group_sport)
                        //group_name
                        gr_tv_info_title_name.setText(dataD!!.group_name)
                        //group_소개
                        gr_tv_info_title_line.setText(dataD!!.intro_comment)

//                        val dateFormat: DateFormat = SimpleDateFormat("YYYY.MM.dd")
//                        val date = dateFormat.format(dataD!!.created_at)
//                        //created_at
//                        gr_tv_info_title_startdate.setText("$date~")

                        //group_maker
                        gr_create_name.setText(dataD!!.group_maker)

                        //recruited_num
                        gr_tv_info_member_recruit_num.setText(dataD!!.recruited_num.toString() + "/")
                        //recruit_num
                        gr_tv_info_member_total_num.setText(dataD!!.recruit_num.toString())
                        //ex_cycle
                        gr_tv_info_cycle_num.setText("주" + dataD!!.ex_cycle.toString() + "회")
                        //ex_intensity
                        gr_tv_info_level_num.setText(dataD!!.ex_intensity)
                        //achive_rate
                        gr_tv_info_percent_num.setText(dataD!!.achive_rate.toString() + "%")
                        //group_sport
                        gr_tv_info_exercise_num.setText(dataD!!.group_sport)

                    } ?: showError(response.errorBody())
            }
        })

    }

    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(this, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }
}