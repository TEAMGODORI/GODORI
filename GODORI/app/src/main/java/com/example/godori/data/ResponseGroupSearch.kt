package com.example.godori.data

data class ResponseGroupSearch(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val group_list: List<searchResult>
    ) {
        data class searchResult(
            val group_name: String,
            val id: Int
        )
    }
}
