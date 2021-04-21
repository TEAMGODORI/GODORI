package com.example.godori.Interface

import com.example.godori.data.RequestCertiUpload
import com.example.godori.data.ResponseCertiUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CertiUpload {
    @Multipart
    @POST("/certi/{userName}")
    fun postCertiUpload(
        @Path("userName") userName: String,
        @Part body: RequestCertiUpload,
//        @Part("description")description: MultipartBody.Builder,
        @Part images: MultipartBody.Part
    ): Call<ResponseCertiUpload>
}
