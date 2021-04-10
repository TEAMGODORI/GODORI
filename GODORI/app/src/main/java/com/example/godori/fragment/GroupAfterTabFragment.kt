package com.example.godori.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.activity.GroupInfoAfterActivity
import com.example.godori.activity.GroupInfoActivity
import com.example.godori.activity.TabBarActivity
import com.example.godori.adapter.GroupAlreadyCertiAdapter
import com.example.godori.adapter.GroupRecruitingInfoAdapter
import com.example.godori.adapter.GroupRecruitingTasteAdapter
import com.example.godori.adapter.GroupTodayCertiAdapter
import com.example.godori.data.ResponseGroupAfterData
import com.example.godori.data.ResponseGroupRecruit
import kotlinx.android.synthetic.main.activity_group_recruiting.*
import kotlinx.android.synthetic.main.fragment_group_after_tab.*
import kotlinx.android.synthetic.main.fragment_group_tab.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupAfterTabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupAfterTabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var dataList: ResponseGroupAfterData? = null
    var memberList: List<ResponseGroupAfterData.Data.Member>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TabBarActivity) {
            var mContext = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_after_tab, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        // 그룹 정보 버튼
        gr_btn_main_group_info.setOnClickListener {
            val intent = Intent(activity, GroupInfoAfterActivity::class.java)
            startActivity(intent)
        }
        // 오늘 날짜
        val cal = Calendar.getInstance()
        val month = (cal.get(Calendar.MONTH) + 1).toString()
        val date = cal.get(Calendar.DATE).toString()
        val dayOfWeekNum = cal.get(Calendar.DAY_OF_WEEK)
        var dayOfMonth = ""
        when (dayOfWeekNum) {
            1 -> dayOfMonth = "일";
            2 -> dayOfMonth = "월";
            3 -> dayOfMonth = "화";
            4 -> dayOfMonth = "수";
            5 -> dayOfMonth = "목";
            6 -> dayOfMonth = "금";
            7 -> dayOfMonth = "토";
        }
        gr_tv_after_day.setText(month + "월 " + date + "일 " + dayOfMonth + "요일")

//        // 오늘 인증한 그룹원 - 리사이클러 뷰
//        viewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        viewAdapter = GroupTodayCertiAdapter()
//        recyclerView = gr_rcv_today_certi.apply {
//            setHasFixedSize(true)
//            // use a linear layout manager
//            layoutManager = viewManager
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//        }
////
//        // 곧 인증할 그룹원 - 리사이클러 뷰
//        viewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        viewAdapter = GroupAlreadyCertiAdapter()
//        recyclerView = gr_rcv_already_certi.apply {
//            setHasFixedSize(true)
//            // use a linear layout manager
//            layoutManager = viewManager
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//        }
//
//    }
    }

    private fun loadData() {
        //Callback 등록하여 통신 요청
        val call: Call<ResponseGroupAfterData> =
            GroupRetrofitServiceImpl.service_gr_after.requestList(
                userName = "김지현" //수정하기
            )
        call.enqueue(object : Callback<ResponseGroupAfterData> {
            override fun onFailure(call: Call<ResponseGroupAfterData>, t: Throwable) {
                // 통신 실패 로직
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ResponseGroupAfterData>,
                response: Response<ResponseGroupAfterData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        // do something
                        dataList = response.body()
                        Log.d("GroupAfterTabFragment", dataList.toString())
                        memberList = dataList!!.data.member_list
                        Log.d("GroupRecruitingActivity", memberList.toString())

                        group_name.setText(it.data.group_name)
                        left_count.setText(it.data.left_count.toString())

                        //adapter에 Member 데이터 넣기
                        setGroupCertiedAdapter(it.data.member_list)

                        //adapter에 Member 데이터 넣기
                        setGroupCertiAdapter(it.data.member_list)
//                        var userTasteArray: MutableList<String> = ArrayList()
//                        userTasteArray.add("주 " + it.data.user.ex_cycle + "회")

                    } ?: showError(response.errorBody())
            }
        })
    }

    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }

    private fun setGroupCertiedAdapter(memberList: List<ResponseGroupAfterData.Data.Member>) {
        gr_rcv_today_certi.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val mAdapter = context?.let { GroupTodayCertiAdapter(dataList, memberList, it) }
        gr_rcv_today_certi.adapter = mAdapter
        gr_rcv_today_certi.setHasFixedSize(true)
    }

    private fun setGroupCertiAdapter(memberList: List<ResponseGroupAfterData.Data.Member>) {
        gr_rcv_already_certi.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val mAdapter = context?.let { GroupAlreadyCertiAdapter(dataList, memberList, it) }
        gr_rcv_already_certi.adapter = mAdapter
        gr_rcv_already_certi.setHasFixedSize(true)
    }
}