package com.example.godori.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.adapter.GroupRecruitingInfoAdapter
import com.example.godori.adapter.GroupRecruitingTasteAdapter
import kotlinx.android.synthetic.main.activity_group_recruiting.*

class GroupRecruitingActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_recruiting)

        // 선택한 취향 recycler view
        viewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = GroupRecruitingTasteAdapter()
        recyclerView = findViewById<RecyclerView>(R.id.gr_rcv_recruiting_taste).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        // 그룹 정보 recycler view
        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewAdapter = GroupRecruitingInfoAdapter()
        recyclerView = findViewById<RecyclerView>(R.id.gr_rcv_recruiting_info).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }
}