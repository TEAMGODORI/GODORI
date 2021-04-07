package com.example.godori.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.godori.R
import com.example.godori.adapter.TabBarAfterViewPagerAdapter
import com.example.godori.adapter.TabBarViewPagerAdapter
import kotlinx.android.synthetic.main.activity_tab_bar.*
import kotlinx.android.synthetic.main.activity_tab_bar_after.*
import kotlin.properties.Delegates

class TabBarAfterActivity : AppCompatActivity() {
    private lateinit var viewpagerAdapter: TabBarAfterViewPagerAdapter
//    private lateinit var certifAdapter: CertifTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_bar_after)

        // 뷰 페이저 세팅
        viewpagerAdapter = TabBarAfterViewPagerAdapter(supportFragmentManager)
        tabbar_after_viewpager.adapter = viewpagerAdapter

        tabbar_after_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tabbar_after.menu.getItem(position).isChecked = true
            }
        })

        // 바텀 네비게이션 세팅
        tabbar_after.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()
            when (it.itemId) {
                R.id.menu_group_after -> index = 0
                R.id.menu_certi_after -> index = 1
                R.id.menu_myinfo_after -> index = 2
            }
            tabbar_after_viewpager.currentItem = index
            true
        }
    }
}