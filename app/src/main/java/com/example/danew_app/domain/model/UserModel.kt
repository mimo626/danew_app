
data class UserModel(
    val userId: String = "",
    val password: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val createdAt: String = "",
    val keywordList: List<String> = emptyList(),
    val customList: List<String>? = emptyList()
)
