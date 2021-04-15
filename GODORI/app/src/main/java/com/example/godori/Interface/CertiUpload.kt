package com.example.godori.Interface

import com.example.godori.data.RequestCertiUpload
import com.example.godori.data.ResponseCertiUpload
import com.example.godori.data.ResponseGroupAfterTab
import retrofit2.Call
import retrofit2.http.*

interface CertiUpload {
    @POST("/certi/{userName}")

//    fun requestList(
//    @Path("userName") userName: String,
//    @Body body: RequestCertiUpload
//    ) : Call<ResponseCertiUpload>

    fun postCertiUpload(
        @Path("userName") userName: String,
        @Body body: RequestCertiUpload
    ): Call<ResponseCertiUpload>
}