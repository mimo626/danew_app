package com.example.danew_app.data.mapper

import UserModel
import com.example.danew.data.local.entity.UserEntity

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
