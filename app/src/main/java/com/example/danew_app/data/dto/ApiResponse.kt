package com.example.danew_app.data.dto

data class ApiResponse<T>(
    val status: String, // "success" / "error"
    val message: String?,
    val data: T?
)