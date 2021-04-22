package com.example.godori.Interface

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface CertifImage {
    @GET("/certi/{userName}")
    @Streaming //용량이 적을 경우 @Streaming은 생략이 가능하다.
    fun certifImageList(
        @Path("userName") userName: String
    ) : Call<ResponseBody>
}