package umc.everyones.everyoneslckmanage.presentation.team

import java.io.Serializable

data class LckCoach(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val teamName: String
) : Serializable