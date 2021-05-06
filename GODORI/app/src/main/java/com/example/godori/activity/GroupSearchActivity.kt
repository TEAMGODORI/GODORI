package com.example.godori.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.adapter.GroupRecruitingInfoAdapter
import com.example.godori.adapter.GroupSearchFileListAdapter
import com.example.godori.data.RequestGroupCreationData
import com.example.godori.data.ResponseGroupCreationData
import com.example.godori.data.ResponseGroupSearch
import kotlinx.android.synthetic.main.activity_certif_tab_upload4.*
import kotlinx.android.synthetic.main.activity_group_search.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class GroupSearchActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    lateinit var group_list: List<ResponseGroupSearch.Data.searchResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_search)

        // 1. 검색할 리스트
//        group_list = listOf("고도리", "고도오리", "고동스", "고목나무", "박지니", "godori")

        val call: Call<ResponseGroupSearch> =
            GroupRetrofitServiceImpl.service_gr_search.groupSearch(
            )
        call.enqueue(object : Callback<ResponseGroupSearch> {
            override fun onFailure(call: Call<ResponseGroupSearch>, t: Throwable) {
                // 통신 실패 로직
            }

            override fun onResponse(
                call: Call<ResponseGroupSearch>,
                response: Response<ResponseGroupSearch>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        Log.v("그룹 목록: ", it.data.group_list.toString())
                        Log.v("그룹 검색: ", "성공!")
                        group_list = it.data.group_list
                        recycler()
                    } ?: showError(response.errorBody())
            }
        })

        // 2. 리사이클러뷰 build
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = GroupSearchFileListAdapter(group_list, this)
        mRecyclerView = findViewById<RecyclerView>(R.id.gr_rcv_search).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = mLayoutManager
            // specify an viewAdapter (see also next example)
            adapter = mAdapter
        }

        // 검색 edit text
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (mAdapter as GroupSearchFileListAdapter)?.filter?.filter(s)
            }
        }
        gr_et_search.addTextChangedListener(textWatcher)
    }

    fun filter(text: String) {
        val filteredList: ArrayList<String> = ArrayList()
        for (item in group_list) {
            if (item.group_name.contains(text)) {
                filteredList.add(item.group_name)
            }
        }
//        mAdapter.filterList(filteredList)
    }

    // 서버 연동관련 에러 함수
    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.v("에러발생", ob.toString())
        Toast.makeText(this, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }

    // 리사이클러 build
    private fun recycler(){
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = GroupSearchFileListAdapter(group_list, this)
        mRecyclerView = findViewById<RecyclerView>(R.id.gr_rcv_search).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = mLayoutManager

            // specify an viewAdapter (see also next example)
            adapter = mAdapter
        }
    }
}