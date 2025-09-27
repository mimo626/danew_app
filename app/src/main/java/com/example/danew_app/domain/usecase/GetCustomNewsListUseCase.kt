package com.example.danew_app.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import com.example.danew_app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCustomNewsListUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val newsRepository: NewsRepository,
) {
    suspend operator fun invoke(token: String): Flow<PagingData<NewsModel>> {
        // 1. 토큰으로 사용자 정보 및 키워드 리스트를 가져옵니다.
        val user = userRepository.getUser(token).toDomain()
        val userKeywordList = user.keywordList

        // 2. 키워드 리스트를 하나의 검색 쿼리 문자열로 결합합니다.
        //    "정치 OR IT OR 경제" 형태로 만들어 PagingSource가 여러 키워드를 검색하도록 유도합니다.
        val combinedQuery = userKeywordList
            .filter { it.isNotBlank() } // 빈 키워드 제거
            .joinToString(separator = " OR ") // OR 연산자로 결합

        // 3. 결합된 쿼리가 비어있는지 확인하고, 비어있으면 빈 PagingData를 반환합니다.
        return if (combinedQuery.isBlank()) {
            flowOf(PagingData.empty())
        } else {
            // 4. 결합된 쿼리를 사용하여 Paging Flow를 반환합니다.
            newsRepository.getNewsBySearchQuery(searchQuery = combinedQuery)
                .map { pagingData ->
                    // 필요한 경우 여기서 DTO -> Domain 매핑 (ViewModel/Repository에서 이미 했다면 생략)
                    pagingData.map { it.toDomain() }
                }
        }
    }
}
