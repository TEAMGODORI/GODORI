package com.example.godori.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.*
import com.example.godori.activity.GroupCreation1Activity
import com.example.godori.activity.GroupInfoActivity
import com.example.godori.activity.GroupRecruitingActivity
import com.example.godori.activity.GroupSearchActivity
import com.example.godori.adapter.GroupMoreAdapter
import com.example.godori.adapter.GroupRecruitingInfoAdapter
import com.example.godori.adapter.GroupTodayCertiAdapter
import com.example.godori.data.ResponseGroupAfterTab
import com.example.godori.data.ResponseGroupBeforeTab
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


class GroupTabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var dataList: ResponseGroupBeforeTab? = null
    var groupList: List<ResponseGroupBeforeTab.Data>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_tab, container, false)

        loadData()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ?????? ??????
        val cal = Calendar.getInstance()
        val month = (cal.get(Calendar.MONTH) + 1).toString()
        val date = cal.get(Calendar.DATE).toString()
        val dayOfWeekNum = cal.get(Calendar.DAY_OF_WEEK)
        var dayOfMonth = ""
        when (dayOfWeekNum) {
            1 -> dayOfMonth = "???";
            2 -> dayOfMonth = "???";
            3 -> dayOfMonth = "???";
            4 -> dayOfMonth = "???";
            5 -> dayOfMonth = "???";
            6 -> dayOfMonth = "???";
            7 -> dayOfMonth = "???";
        }
        gr_tv_main_day.setText(month + "??? " + date + "??? " + dayOfMonth + "??????")

        // ?????? ?????? ?????? - ??????????????? ???
        viewManager = GridLayoutManager(activity, 2)
        viewAdapter = GroupMoreAdapter(groupList, context)
        recyclerView = gr_rcv_main_more.apply {
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        // ?????? ??????
        gr_btn_main_creation.setOnClickListener {
            val intent = Intent(activity, GroupCreation1Activity::class.java)
            startActivity(intent)
        }
        // ????????? ??????
        btnMoreGroup.setOnClickListener {
            val intent = Intent(activity, GroupRecruitingActivity::class.java)
            startActivity(intent)
        }
        // ?????? ??????
        gr_btn_main_search.setOnClickListener {
            val intent = Intent(activity, GroupSearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadData() {
        //Callback ???????????? ?????? ??????
        val call: Call<ResponseGroupBeforeTab> =
            GroupRetrofitServiceImpl.service_gr_before_list.requestList(

            )
        call.enqueue(object : Callback<ResponseGroupBeforeTab> {
            override fun onFailure(call: Call<ResponseGroupBeforeTab>, t: Throwable) {
                // ?????? ?????? ??????
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ResponseGroupBeforeTab>,
                response: Response<ResponseGroupBeforeTab>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        // do something
                        dataList = response.body()
                        groupList = dataList!!.data
                        Log.d("GroupTabFragment", groupList.toString())

                        //???????????? ????????? ??????
                        setGroupMoreAdapter(groupList!!)

                    } ?: showError(response.errorBody())
            }
        })
    }

    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }

    private fun setGroupMoreAdapter(groupList: List<ResponseGroupBeforeTab.Data>) {
        val mAdapter = GroupMoreAdapter(groupList, context)
        gr_rcv_main_more.adapter = mAdapter
        mAdapter.itemClick = object : GroupMoreAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val groupId = groupList!![position].id
                val intent = Intent(activity, GroupInfoActivity::class.java)
                intent.putExtra("groupId", groupId)
                startActivity(intent)
            }
        }
        mAdapter.notifyDataSetChanged()
        gr_rcv_main_more.setHasFixedSize(true)
    }
}