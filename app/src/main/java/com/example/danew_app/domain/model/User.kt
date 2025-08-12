package com.example.danew.domain.model

data class User (
    val userId: String,
    val password: String,
    val name: String,
    val age: Int,
    val keyWordList: List<String>,
    val diaryContent: String?,
    // String 뉴스 아이디를 저장해서 모음
    val bookmarkNewsList: List<String>?,
    // 검색한 키워드, 클릭한 뉴스의 키워드를 모아서 맞춤형 뉴스 추천
    val customNewsKeywordList: List<String>?,
)