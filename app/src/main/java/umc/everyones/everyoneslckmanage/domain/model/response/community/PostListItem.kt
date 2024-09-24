package umc.everyones.everyoneslckmanage.domain.model.response.community


data class PostListItem(
    val postId: Int,
    val title: String,
    val date: String,
    val nickname: String,
    val favoriteTeam: String,
    val commentCnt: Int,
    val image: String
)
