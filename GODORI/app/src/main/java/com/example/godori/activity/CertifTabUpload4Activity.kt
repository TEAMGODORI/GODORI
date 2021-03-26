package com.example.godori.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.godori.R
import com.example.godori.fragment.CertifTabFragment
import kotlinx.android.synthetic.main.activity_certif_tab_upload3.*
import kotlinx.android.synthetic.main.activity_certif_tab_upload4.*

class CertifTabUpload4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_upload4)

        backBtn4.setOnClickListener {
            //백버튼 눌렀을 때
            onBackPressed()
        }

        val certifTabFragment = CertifTabFragment()
        btn_complete.setOnClickListener(View.OnClickListener {
            replaceFragment(certifTabFragment)
        })
    }

    // Fragment로 이동
    fun replaceFragment(certifTabFragment: Fragment?) {
        val certifTabFragment = CertifTabFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.certifTabFrag, certifTabFragment)
        fragmentTransaction.commit()
    }
}