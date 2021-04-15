package com.example.godori.Interface

import com.example.godori.data.RequestGroupCreationData
import com.example.godori.data.RequestTasteSetting
import com.example.godori.data.ResponseGroupCreationData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface TasteSetting {
    @PUT("/mypage/{userName}")
    fun postTaste(
        @Path("userName") userName: String,
        @Body body: RequestTasteSetting
    ): Call<ResponseGroupCreationData>
}