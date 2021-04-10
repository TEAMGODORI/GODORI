package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.godori.R
import com.example.godori.fragment.CertifTabFragment
import kotlinx.android.synthetic.main.activity_certif_tab_upload4.*
import kotlinx.android.synthetic.main.activity_certif_tab_upload4.view.*
import java.lang.Boolean.FALSE


class CertifTabUpload4Activity : AppCompatActivity() {
    // 데이터 목록
    var ex_comment: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_upload4)

        // 코멘트에 textWatcher
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btn_complete.isEnabled = true
                ex_comment = certi_upload_comment.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                btn_complete.isEnabled = false

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_complete.isEnabled = true
            }
        }
        certi_upload_comment.addTextChangedListener(textWatcher)

        // 이전
        backBtn4.setOnClickListener {
            //백버튼 눌렀을 때
            onBackPressed()
        }

        val certifTabFragment = CertifTabFragment()
//        btn_complete.setOnClickListener(View.OnClickListener {
//            replaceFragment(certifTabFragment)
//        })

        // 완료
        btn_complete.setOnClickListener(View.OnClickListener {
            // 이전 뷰 스택 다 지우고 TabBar 액티비티로 돌아가기
            val intent = Intent(application, TabBarActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)
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