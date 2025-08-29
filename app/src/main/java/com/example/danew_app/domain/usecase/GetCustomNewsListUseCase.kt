package com.example.danew_app.domain.usecase

import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class GetCustomNewsListUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val newsRepository: NewsRepository,
) {
    suspend operator fun invoke(token: String): Map<String, List<NewsModel>> {
        val user = userRepository.getUser(token).toDomain()
        val userKeywordList = user.keywordList

        // 키워드 -> 뉴스리스트 형태로 맵핑
        return userKeywordList.associateWith { keyword ->
            newsRepository
                .getNewsBySearchQuery(searchQuery = keyword, loadMore = false)
                .map { it.toDomain() }
        }
    }
}
