package com.example.godori.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.activity.GroupSearchActivity
import com.example.godori.activity.TasteSettingActivity
import com.example.godori.adapter.GroupRecruitingTasteAdapter
import com.example.godori.adapter.MyInfoPictureAdapter
import kotlinx.android.synthetic.main.activity_group_recruiting.*
import kotlinx.android.synthetic.main.fragment_my_info_tab.*

class MyInfoTabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_my_info_tab, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 피드 사진 recycler view
        viewManager = GridLayoutManager(activity, 3)
        viewAdapter = MyInfoPictureAdapter()
        recyclerView = my_rcv_picture.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        // 설정 버튼
        my_btn_setting.setOnClickListener {
            val intent = Intent(activity, GroupSearchActivity::class.java)
            startActivity(intent)
        }

        // 취향 버튼
        my_btn_taste.setOnClickListener {
            val intent = Intent(activity, TasteSettingActivity::class.java)
            startActivity(intent)
        }
    }
}