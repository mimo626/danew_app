package com.example.danew_app.presentation.category

object NewsCategory {
    val categoryKrToEn = mapOf(
        "인기" to "top",
        "정치" to "politics",
        "경제" to "business",
        "엔터" to "entertainment",
        "스포츠" to "sports",
        "기술" to "technology",
        "과학" to "science",
        "세계" to "world",
        "건강" to "health",
        "일상" to "lifestyle"
    )

    val categoryEnToKr = categoryKrToEn.entries.associate { (k, v) -> v to k }

    fun getEnFromKr(kr: String): String? = categoryKrToEn[kr]
    fun getKrFromEn(en: String): String? = categoryEnToKr[en]
}
