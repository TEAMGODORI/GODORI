package com.example.godori.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.adapter.TabBarViewPagerAdapter
import com.example.godori.data.ResponseGroupAfterTab
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_tab_bar.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class TabBarActivity : AppCompatActivity() {
    private lateinit var viewpagerAdapter: TabBarViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_bar)

        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            Log.d("TabBarActivity", "회원번호: ${user?.id}")
            Log.d("TabBarActivity", "닉네임: ${user?.kakaoAccount?.profile?.nickname}")
            Log.d("TabBarActivity", "프로필 링크: ${user?.kakaoAccount?.profile?.profileImageUrl}")
            Log.d("TabBarActivity", "썸네일 링크: ${user?.kakaoAccount?.profile?.thumbnailImageUrl}")

//            loadData(user?.kakaoAccount?.profile?.nickname, user?.kakaoAccount?.profile?.profileImageUrl)
        }

        loadData()
        tabbar_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tabbar.menu.getItem(position).isChecked = true
            }
        })

        // 바텀 네비게이션 세팅
        tabbar.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()
            when (it.itemId) {
                R.id.menu_group -> index = 0
                R.id.menu_certi -> index = 1
                R.id.menu_myinfo -> index = 2
            }
            tabbar_viewpager.currentItem = index
            true
        }
    }
    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
    }

    fun loadData(){
        val call: Call<ResponseGroupAfterTab> =
            GroupRetrofitServiceImpl.service_gr_after_tab.requestList(
                userName = "김지현"//수정하기
            )
        call.enqueue(object : Callback<ResponseGroupAfterTab> {
            override fun onFailure(call: Call<ResponseGroupAfterTab>, t: Throwable) {
                // 통신 실패 로직
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ResponseGroupAfterTab>,
                response: Response<ResponseGroupAfterTab>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        // do something
                        var group = it.data.group_id
                        // 뷰 페이저 세팅
                        Log.v("group_string", group.toString())
                        viewpagerAdapter = TabBarViewPagerAdapter(supportFragmentManager, group)
                        tabbar_viewpager.adapter = viewpagerAdapter
                        Log.v("onResponse", group.toString())
                    } ?: showError(response.errorBody())
            }
        })
    }
}
