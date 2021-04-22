package com.example.godori.Interface

import com.example.godori.data.ResponseCertiUpload
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CertiUpload {
    @Multipart
    @POST("/certi/{userName}")
    fun postCertiUpload(
        @Path("userName") userName: String,

//        @Body body: RequestCertiUpload,

//        @Path("ex_time") ex_time: String,
//        @Path("ex_intensity") ex_intensity: String,
//        @Path("ex_evalu") ex_evalu: String,
//        @Path("ex_comment") ex_comment: String,
//        @Path("certi_sport") certi_sport: String,

//        @Field("ex_time") ex_time: String,
//        @Field("ex_intensity") ex_intensity: String,
//        @Field("ex_evalu") ex_evalu: String,
//        @Field("ex_comment") ex_comment: String,
//        @Field("certi_sport") certi_sport: String,

//        @Part("ex_time") ex_time: RequestBody,
//        @Part("ex_intensity") ex_intensity: RequestBody,
//        @Part("ex_evalu") ex_evalu: RequestBody,
//        @Part("ex_comment") ex_comment: RequestBody,
//        @Part("certi_sport") certi_sport: RequestBody,

        @Part("ex_time") ex_time: String,
        @Part("ex_intensity") ex_intensity: String,
        @Part("ex_evalu") ex_evalu: String,
        @Part("ex_comment") ex_comment: String,
        @Part("certi_sport") certi_sport: String,

        @Part("images") images: RequestBody
    ): Call<ResponseCertiUpload>
}