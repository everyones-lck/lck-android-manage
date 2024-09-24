package umc.everyones.everyoneslckmanage.domain.model.response.community

data class Comment(
    val commentId: Int,
    val nickname: String,
    val favoriteTeam: String,
    val body: String,
    val date: String,
    val profileImageUrl: String,
    val category: String
)
