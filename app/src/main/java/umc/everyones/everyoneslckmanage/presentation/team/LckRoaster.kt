package umc.everyones.everyoneslckmanage.presentation.team

import java.io.Serializable

data class LckRoaster(
    val id: String,
    val name: String,
    val position: String,
    val imageUrl: String
) : Serializable
