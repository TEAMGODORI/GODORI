package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.godori.R
import com.example.godori.fragment.CertifTabFragment
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
//        btn_complete.setOnClickListener(View.OnClickListener {
//            replaceFragment(certifTabFragment)
//        })
        btn_complete.setOnClickListener(View.OnClickListener {
//            val intent = Intent(CertifTabUpload4Activity)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(intent)
//            finish()
        })

    }

    // Fragment로 이동
    @SuppressLint("RestrictedApi")
    fun replaceFragment(fragment: Fragment?) {
//        val certifTabFragment = CertifTabFragment()
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.certifTabFrag, certifTabFragment)
//        fragmentTransaction.commit()
        val fragmentManager = supportFragmentManager
//        val fragmentManager: FragmentManager = getActivity().getFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.menu_certi, fragment!!)
        fragmentTransaction.commit()
    }
}