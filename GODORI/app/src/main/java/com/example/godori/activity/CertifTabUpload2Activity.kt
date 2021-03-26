package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_certif_tab_upload2.*


class CertifTabUpload2Activity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_upload2)

        //백버튼 눌렀을 때
        backBtn2.setOnClickListener {
            onBackPressed()
        }

        next2Btn.setOnClickListener {
            val intent = Intent(this, CertifTabUpload3Activity::class.java)
            startActivity(intent)
        }

        //스피너
        val items = resources.getStringArray(R.array.ct_time_arrary)

        //스피너 어댑터
        //어댑터의 아이템은 안드로이드 스튜디오에서 제공해 주는 기본
        //android.R.layout.simple_spinner_dropdown_item 을 사용
        val myAapter = object : ArrayAdapter<String>(this, R.layout.ct_time_spinner) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val v = super.getView(position, convertView, parent)

                if (position == count) {
                    //마지막 포지션의 textView 를 힌트 용으로 사용합니다.
                    (v.findViewById<View>(R.id.ctTimeSpinner) as TextView).text = ""
                    //아이템의 마지막 값을 불러와 hint로 추가해 줍니다.
                    (v.findViewById<View>(R.id.ctTimeSpinner) as TextView).hint = getItem(count)
                }

                return v
            }

            override fun getCount(): Int {
                //마지막 아이템은 힌트용으로만 사용하기 때문에 getCount에 1을 빼줍니다.
                return super.getCount() - 1
            }
        }


//아이템을 추가해 줍니다.
        myAapter.addAll(items.toMutableList())

//힌트로 사용할 문구를 마지막 아이템에 추가해 줍니다.
        myAapter.add("시간 선택")

//어댑터를 연결해줍니다.
        timeSpinner.adapter = myAapter

//스피너 초기값을 마지막 아이템으로 설정해 줍니다. (마지막 아이템이 힌트 이기 때문이죠)
        timeSpinner.setSelection(myAapter.count)

//droplist를 spinner와 간격을 두고 나오게 해줍니다.)
//아이템 크기가 45dp 이므로 45dp 간격을 주었습니다.
//이때 dp 를 px 로 변환해 주는 작업이 필요합니다.
        timeSpinner.dropDownVerticalOffset = dipToPixels(45f).toInt()


//스피너 선택시 나오는 화면 입니다.
        timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    //...
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("MyTag", "onNothingSelected")
            }
        }
    }
    private fun dipToPixels(dipValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dipValue,
            resources.displayMetrics
        )
    }
}