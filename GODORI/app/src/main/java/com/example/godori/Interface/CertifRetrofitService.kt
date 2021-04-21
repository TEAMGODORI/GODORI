package com.example.godori.Interface

import com.example.godori.data.ResponseCertiTab
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

// 서버에서 호출할 메서드를 선언하는 인터페이스
interface CertifRetrofitService {
    @GET("/certi/{userName}")
    @Streaming //용량이 적을 경우 @Streaming은 생략이 가능하다.
    fun requestList(
        @Path("userName") userName: String,
        @Query("date") date: String
    ) : Call<ResponseCertiTab>
}