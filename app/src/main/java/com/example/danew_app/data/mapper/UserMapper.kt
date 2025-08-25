package com.example.danew_app.data.mapper

import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.domain.model.UserModel

// Entity -> Domain
fun UserEntity.toDomain(): UserModel {
    return UserModel(
        userId = this.userId,
        password = this.password,
        name = this.name,
        age = this.age,
        gender = this.gender,
        createdAt = this.createdAt,
        keywordList = this.keywordList,
        customList = this.customList
    )
}

// Domain -> Entity
fun UserModel.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        password = this.password,
        name = this.name,
        age = this.age,
        gender = this.gender,
        createdAt = this.createdAt,
        keywordList = this.keywordList,
        customList = this.customList
    )
}
