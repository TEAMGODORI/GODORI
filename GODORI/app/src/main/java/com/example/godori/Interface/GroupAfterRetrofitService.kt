package com.example.godori.Interface

import com.example.godori.data.ResponseGroupAfterData
import com.example.godori.data.ResponseGroupRecruit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GroupAfterRetrofitService {
    @Headers("Content-Type:application/json")
    @GET("/group/member/{userName}")
    fun requestList(
        @Path("userName") userName: String
    ) : Call<ResponseGroupAfterData>
}