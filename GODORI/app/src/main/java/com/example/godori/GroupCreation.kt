package com.example.godori

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupCreation {
    @POST("/group")
    fun postGroupCreation(
        @Body body: RequestGroupCreationData
    ): Call<ResponseGroupCreationData>
}