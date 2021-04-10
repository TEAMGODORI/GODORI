package com.example.godori

import com.example.godori.data.RequestGroupCreationData
import com.example.godori.data.ResponseCertiUpload
import com.example.godori.data.ResponseGroupCreationData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CertiUpload {
    @POST("/certi")
    fun postGroupCreation(
        @Body body: RequestGroupCreationData
    ): Call<ResponseCertiUpload>
}