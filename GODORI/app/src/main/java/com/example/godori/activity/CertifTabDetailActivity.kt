package com.example.godori.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.data.ResponseCertiDetail
import com.example.godori.data.ResponseGroupInfo
import kotlinx.android.synthetic.main.activity_certif_tab_detail.*
import kotlinx.android.synthetic.main.activity_group_info.*
import kotlinx.android.synthetic.main.activity_group_recruiting.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI

class CertifTabDetailActivity : AppCompatActivity() {
    var data : ResponseCertiDetail.Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_detail)

        // 데이터 베이스에서 Person마다 정보 가져오기
        backBtn.setOnClickListener {
            onBackPressed()
        }
        //certiId 가져오기
        val certiImgId = intent.extras!!.getInt("certiImgId")
        Log.d("certiImgId", certiImgId.toString())
        loadData(certiImgId)
    }

    private fun loadData(certiImgId: Int) {
        //group_id로 그룹 정보 가져오기
        val call: Call<ResponseCertiDetail> =
            GroupRetrofitServiceImpl.service_ct_detail.requestList(
                userName = "김지현",
                certiId = certiImgId // 수정하기
            )
        call.enqueue(object : Callback<ResponseCertiDetail> {
            override fun onFailure(call: Call<ResponseCertiDetail>, t: Throwable) {
                // 통신 실패 로직
            }

            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onResponse(
                call: Call<ResponseCertiDetail>,
                response: Response<ResponseCertiDetail>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        // do something
                        data = response.body()?.data
                        Log.d("GroupRecruitingActivity", data.toString())

                        my_tv_userName.setText(data?.user_name)
//                        my_iv_profile.setImageURI(data?.user_image?.toUri())

                        val certiImgUrl: String = data!!.certi_images

                        if (certiImgUrl.length > 0) {
                            Glide.with(this@CertifTabDetailActivity)
                                .load(certiImgUrl)
                                .error(android.R.drawable.stat_notify_error)
                                .into(certiImg)

                        } else {
                            Glide.with(this@CertifTabDetailActivity)
                                .load(R.drawable.certif_un)
                                .error(android.R.drawable.stat_notify_error)
                                .into(certiImg)
                        }

                        time_exercise.setText(data?.ex_time)

                        val sportList = it.data.sports.split(",")
                        val textArray = arrayOf<TextView>(
                            exercise1,
                            exercise2,
                            exercise3,
                            exercise4,
                            exercise5,
                            exercise6
                        )
                        for (i in sportList.indices) {
                            textArray[i].setVisibility(View.VISIBLE)
                            textArray[i].setText(sportList[i])
                        }

                        intensity.setText(data?.ex_intensity)
                        reviews.setText(data?.ex_evalu)
                        comment.setText(data?.ex_comment)
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