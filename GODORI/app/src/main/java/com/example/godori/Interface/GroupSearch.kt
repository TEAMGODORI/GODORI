package com.example.godori.Interface

import com.example.godori.data.RequestGroupCreationData
import com.example.godori.data.ResponseGroupCreationData
import com.example.godori.data.ResponseGroupSearch
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupSearch {
    @POST("/group/join/{userName}")
    fun postGroupCreation(
        @Path("userName") userName: String
    ): Call<ResponseGroupSearch>
}