package com.example.danew.domain.model

import kotlinx.datetime.LocalDateTime

data class User (
    val userId: String,
    val password: String,
    val name: String,
    val age: Int,
    val gender: String,
    val createdAt: LocalDateTime,
    val keyWordList: List<String>,
    // 검색한 키워드, 클릭한 뉴스의 키워드를 모아서 맞춤형 뉴스 추천
    val customList: List<String>?,
)