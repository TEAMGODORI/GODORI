package com.example.godori

import com.example.godori.Interface.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GroupRetrofitServiceImpl {
    private const val BASE_URL = "http://15.164.186.213:3000/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 그룹 - 모집중인인 그룹
    val service_gr_recruit: GroupRetrofitService = retrofit.create(GroupRetrofitService::class.java)

    // 그룹 - 그룹 생성
    val service_gr_creation: GroupCreation = retrofit.create(GroupCreation::class.java)

    // 그룹 - 가입
    val service_gr_join: GroupJoin = retrofit.create(GroupJoin::class.java)

    // 그룹 - 그룹후 화면
    val service_gr_after_tab: GroupAfter = retrofit.create(GroupAfter::class.java)

    // 그룹 - 그룹 탈퇴
    val service_gr_exit: GroupExit = retrofit.create(GroupExit::class.java)

    // 인증 - 업로드
    val service_ct_upload: CertiUpload = retrofit.create(CertiUpload::class.java)

    // 취향 설정
    val service_taste: TasteSetting = retrofit.create(TasteSetting::class.java)

    //그룹 - 가입후 그룹
    val service_gr_after : GroupAfterRetrofitService = retrofit.create(GroupAfterRetrofitService::class.java)

    //그룹 - 가입 전 상세보기
    val service_gr__info : GroupInfoRetrofitService = retrofit.create(GroupInfoRetrofitService::class.java)

    //그룹 - 가입 후 상세보기
    val service_gr__info_after : GroupInfoAfterRetrofitService = retrofit.create(GroupInfoAfterRetrofitService::class.java)

    //인증 - 메인
    val service_ct_tab : CertifRetrofitService = retrofit.create(CertifRetrofitService::class.java)
}