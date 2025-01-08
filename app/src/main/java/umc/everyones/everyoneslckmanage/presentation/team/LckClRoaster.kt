package umc.everyones.everyoneslckmanage.presentation.team

import java.io.Serializable

data class LckClRoaster(
    val id: Int,
    val name: String,
    val position: String,
    val imageUrl: String,
    val teamName: String
) : Serializable