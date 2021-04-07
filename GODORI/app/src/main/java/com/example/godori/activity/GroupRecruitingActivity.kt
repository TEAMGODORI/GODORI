package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.*
import com.example.godori.adapter.GroupRecruitingInfoAdapter
import com.example.godori.adapter.GroupRecruitingTasteAdapter
import com.example.godori.data.ResponseGroupRecruit
import kotlinx.android.synthetic.main.activity_group_recruiting.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupRecruitingActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var dataList: ResponseGroupRecruit? = null
    var groupList: List<ResponseGroupRecruit.Data.Group>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_recruiting)
        loadData()

//        // 선택한 취향 recycler view
//        viewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        viewAdapter = GroupRecruitingTasteAdapter()
//        recyclerView = findViewById<RecyclerView>(R.id.gr_rcv_recruiting_taste).apply {
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            setHasFixedSize(true)
//
//            // use a linear layout manager
//            layoutManager = viewManager
//
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//        }

//        // 그룹 정보 recycler view
//        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        viewAdapter = GroupRecruitingInfoAdapter()
//        recyclerView = findViewById<RecyclerView>(R.id.gr_rcv_recruiting_info).apply {
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            setHasFixedSize(true)
//            // use a linear layout manager
//            layoutManager = viewManager
//
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//        }

        // 취향 버튼
        gr_btn_recruiting_modify.setOnClickListener {
            val intent = Intent(this, TasteSettingActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadData(){
        //Callback 등록하여 통신 요청
        val call: Call<ResponseGroupRecruit> =
            GroupRetrofitServiceImpl.service_gr_recruit.requestList(
                userName = "김지현" //수정하기
            )
        call.enqueue(object : Callback<ResponseGroupRecruit> {
            override fun onFailure(call: Call<ResponseGroupRecruit>, t: Throwable) {
                // 통신 실패 로직
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ResponseGroupRecruit>,
                response: Response<ResponseGroupRecruit>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        // do something
                        dataList = response.body()
                        groupList = dataList!!.data.group_list
                        Log.d("GroupRecruitingActivity", groupList.toString())
                        //adapter에 Group 데이터 넣기
                        setGroupAdapter(it.data.group_list)

                        gr_tv_recruiting_groupNum.setText(it.data.group_list.size.toString()+"건")

                        //adapter에 User 데이터 넣기
                        setTasteAdapter(it.data.user)
                    } ?: showError(response.errorBody())
            }
        })
    }
    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(this, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }
    private fun setGroupAdapter(groupList : List<ResponseGroupRecruit.Data.Group>){
        gr_rcv_recruiting_info.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        val mAdapter = GroupRecruitingInfoAdapter(groupList,this)
        gr_rcv_recruiting_info.adapter = mAdapter
        gr_rcv_recruiting_info.setHasFixedSize(true)
    }
    private fun setTasteAdapter(user : ResponseGroupRecruit.Data.User){
        gr_rcv_recruiting_taste.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val mAdapter = GroupRecruitingTasteAdapter(user,this)
        gr_rcv_recruiting_taste.adapter = mAdapter
        gr_rcv_recruiting_taste.setHasFixedSize(true)
    }
}