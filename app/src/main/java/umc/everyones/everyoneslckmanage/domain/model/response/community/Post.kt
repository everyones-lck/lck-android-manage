package umc.everyones.everyoneslckmanage.domain.model.response.community

import java.io.Serializable

data class Post(
    val postId: Int,
    val writer: String,
    val title: String,
    val body: String,
    val category: String
): Serializable
