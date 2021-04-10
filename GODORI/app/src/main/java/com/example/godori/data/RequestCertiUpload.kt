package com.example.godori.data

import java.io.File

data class RequestCertiUpload (
    val ex_time	: String,
    val ex_intensity : String,
    val ex_evalu : String,
    val ex_comment	: String,
    val images: File
)