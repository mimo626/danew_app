package com.example.danew_app.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaryModel (
    val diaryId: String = "",
    val createdAt: String = "",
    val content: String? = "",
):Parcelable