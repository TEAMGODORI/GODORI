package com.example.godori.data

data class ResponseGroupAfterData(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val group_cycle: Int,
        val group_id: Int,
        val group_name: String,
        val left_count: Int,
        val member_list: List<Member>
    ) {
        data class Member(
            val today_done: Boolean,
            val user_id: Int,
            val user_img: String,
            val user_name: String,
            val week_count: Int
        )
    }
}