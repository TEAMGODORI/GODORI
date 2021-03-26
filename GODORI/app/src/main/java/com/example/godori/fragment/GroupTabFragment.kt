package com.example.godori.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.activity.GroupCreation1Activity
import com.example.godori.activity.GroupRecruitingActivity
import com.example.godori.activity.GroupSearchActivity
import com.example.godori.adapter.GroupMoreAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_group_info.*
import kotlinx.android.synthetic.main.fragment_group_tab.*
import java.util.*


class GroupTabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_tab, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        gr_tv_main_day.setText(month + "월 " + date + "일 " + dayOfMonth + "요일")

        // 모집 중인 그룹 - 리사이클러 뷰
        viewManager = GridLayoutManager(activity, 2)
        viewAdapter = GroupMoreAdapter()
        recyclerView = gr_rcv_main_more.apply {
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        // 생성 버튼
        gr_btn_main_creation.setOnClickListener {
            val intent = Intent(activity, GroupCreation1Activity::class.java)
            startActivity(intent)
        }
        // 더보기 버튼
        btnMoreGroup.setOnClickListener {
            val intent = Intent(activity, GroupRecruitingActivity::class.java)
            startActivity(intent)
        }
        // 검색 버튼
        gr_btn_main_search.setOnClickListener {
            val intent = Intent(activity, GroupSearchActivity::class.java)
            startActivity(intent)
        }
    }
}