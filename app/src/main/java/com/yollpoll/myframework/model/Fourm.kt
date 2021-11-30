package com.example.nmbcompose.bean

import com.squareup.moshi.JsonClass

class ForumList : ArrayList<Forum>()

//板块
data class Forum(
    val forums: List<ForumDetail>,
    val id: String,
    val name: String,
    val sort: String,
    val status: String
)

//详细版本
data class ForumDetail(
    val createdAt: String,
    val fgroup: String,
    val id: String,
    val interval: String,
    val msg: String,
    val name: String,
    val showName: String,
    val sort: String,
    val status: String,
    val updateAt: String
)