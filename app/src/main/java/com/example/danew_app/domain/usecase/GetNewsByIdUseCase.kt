//package com.example.danew_app.domain.usecase
//
//import com.example.danew_app.data.entity.NewsEntity
//import com.example.danew_app.data.mapper.toDomain
//import com.example.danew_app.domain.model.NewsModel
//import com.example.danew_app.domain.repository.NewsRepository
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class GetNewsByIdUseCase @Inject constructor(
//    private val repository: NewsRepository
//) {
//    suspend operator fun invoke(id: String): Flow<NewsModel> {
//        return repository.getNewsById(id).toDomain()
//    }
//}
