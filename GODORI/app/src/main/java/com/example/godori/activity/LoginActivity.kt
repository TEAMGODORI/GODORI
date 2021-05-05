package com.example.godori.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.godori.R
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //카카오톡 로그인 해시키
        var keyHash = Utility.getKeyHash(this)
        Log.d("KEY_HASH", keyHash)

        //카카오 SDK를 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}