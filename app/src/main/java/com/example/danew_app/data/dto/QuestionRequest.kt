package com.example.danew_app.data.dto

import com.google.gson.annotations.SerializedName

data class QuestionRequest (
    @SerializedName("question")
    val question : String,
    @SerializedName("answer")
    val answer : String,
)