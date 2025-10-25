package com.example.danew_app.domain.model

data class NewsModel(
    val newsId: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String? = "",
    val sourceName: String = "",
    val pubDate: String = "",
    val category: List<String>? = emptyList(),
    val creator: List<String>? = emptyList(),
    val keywords: List<String>? = emptyList(),
    val link: String? = "",
    val language: String? = "",)
