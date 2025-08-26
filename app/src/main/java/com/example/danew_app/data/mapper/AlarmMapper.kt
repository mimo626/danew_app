package com.example.danew_app.data.mapper

import com.example.danew_app.data.entity.AlarmEntity
import com.example.danew_app.domain.model.AlarmModel

fun AlarmEntity.toDomain() : AlarmModel {
    return AlarmModel(
        alarmId = this.alarmId,
        userId = this.userId,
        message = this.message,
        createdAt = this.createdAt,
        isRead = this.isRead,
    )
}