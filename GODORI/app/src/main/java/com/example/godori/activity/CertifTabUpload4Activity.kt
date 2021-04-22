package com.example.godori.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.godori.GroupRetrofitServiceImpl
import com.example.godori.R
import com.example.godori.data.ResponseCertiUpload
import com.example.godori.fragment.CertifTabFragment
import kotlinx.android.synthetic.main.activity_certif_tab_upload4.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class CertifTabUpload4Activity : AppCompatActivity() {
    // 데이터 목록
    var ex_comment: String = ""
    var ex_time: String = ""
    var ex_intensity: String = ""
    var ex_evalu: String = ""
    var certi_sport: String = ""
    lateinit var images: File

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_upload4)

        //백 버튼 눌렀을 때
//        backBtn3.setOnClickListener {
//            onBackPressed()
//        }

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

//        // 이전
//        backBtn4.setOnClickListener {
//            onBackPressed()
//        }

        // 완료
        btn_complete.setOnClickListener(View.OnClickListener {
            // 1. 데이터 전달 받음
            val secondIntent = getIntent()
            ex_time = secondIntent.getStringExtra("ex_time").toString()
            ex_intensity = secondIntent.getStringExtra("ex_intensity").toString()
            ex_evalu = secondIntent.getStringExtra("ex_intensity").toString()
            certi_sport = secondIntent.getStringExtra("certi_sport").toString()

            // 2. 이미지 Multipart.Part로 변환
            images = secondIntent.getSerializableExtra("images") as File

            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), images)

            val body: MultipartBody.Part = MultipartBody.Part.createFormData("images", images.name, requestFile)

            // 3. body build
            var requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("ex_time", ex_time)
                .addFormDataPart("ex_intensity",ex_intensity)
                .addFormDataPart("ex_evalu",ex_evalu)
                .addFormDataPart("certi_sport",certi_sport)
                .addFormDataPart("ex_comment",ex_comment)
                .build()


            // 3. 인증 업로드 POST
            val call: Call<ResponseCertiUpload> =
                GroupRetrofitServiceImpl.service_ct_upload.postCertiUpload(
                    // parameter
                    "김지현",
                    // body
//                    RequestCertiUpload(
//                        ex_time = ex_time,
//                        ex_intensity = ex_intensity,
//                        ex_evalu = ex_evalu,
//                        certi_sport = certi_sport,
//                        ex_comment = ex_comment,
//                        images =  body
//                    )


                    ex_time = ex_time,
                    ex_intensity = ex_intensity,
                    ex_evalu = ex_evalu,
                    certi_sport = certi_sport,
                    ex_comment = ex_comment,
//                    requestBody,

//                    images =  body
                images = requestFile
                )
            call.enqueue(object : Callback<ResponseCertiUpload> {
                override fun onFailure(call: Call<ResponseCertiUpload>, t: Throwable) {
                    // 통신 실패 로직
                    Log.d("업로드 실패 : ", t.message.toString())
                }
                override fun onResponse(
                    call: Call<ResponseCertiUpload>,
                    response: Response<ResponseCertiUpload>
                ) {
                    response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let { it ->
                            Log.d("업로드 성공 : ", response.body().toString())
//                            val intent = Intent(application, TabBarActivity::class.java)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
//                            // 4. 액티비티 시작
//                            startActivity(intent)
                        } ?: showError(response.errorBody())
                }

            })
            // 3. 이전 뷰 스택 다 지우고 TabBar 액티비티로 돌아가기
            val intent = Intent(application, TabBarActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // 4. 액티비티 시작
            startActivity(intent)
        })

    }


    val certifTabFragment = CertifTabFragment()
    //        btn_complete.setOnClickListener(View.OnClickListener {
//            replaceFragment(certifTabFragment)
//        })


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

    // 서버 연동관련 에러 함수
    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())

//        Toast.makeText(this, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }

    // 뒤로가기 함수
//    override fun onBackPressed() {
//        startActivity(Intent(this, CertifTabUpload4Activity::class.java))
//        finish()
//    }
}
