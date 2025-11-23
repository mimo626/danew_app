package com.example.danew_app.domain.repository

import com.example.danew_app.data.dto.AnnounceRequest
import com.example.danew_app.data.entity.AnnounceEntity

interface AnnounceRepository {
    suspend fun saveAnnounce(announceRequest: AnnounceRequest): AnnounceEntity
    suspend fun getAnnounceList(): List<AnnounceEntity>
    suspend fun deleteAnnounce(announceId: String): String
}