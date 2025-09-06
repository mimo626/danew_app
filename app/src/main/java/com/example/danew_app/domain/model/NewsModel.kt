package com.example.danew_app.domain.model

data class NewsModel(
    val newsId: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val sourceName: String,
    val pubDate: String,
    val category: List<String>?,
    val creator: List<String>?,
    val keywords: List<String>?,
    val link: String?,
    val language: String?,)
