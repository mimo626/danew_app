package com.example.danew_app.data.entity

data class MetaNewsEntity (
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val sourceName: String,
    val pubDate: String,
    val category: List<String>?,
    val creator: List<String>?,
    val keywords: List<String>?,
    val link: String?,
    val language: String?,
    val savedAt:String,
)