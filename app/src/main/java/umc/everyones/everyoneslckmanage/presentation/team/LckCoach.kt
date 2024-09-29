package umc.everyones.everyoneslckmanage.presentation.team

import java.io.Serializable

data class LckCoach(
    val id: String,
    val name: String,
    val imageUrl: String
) : Serializable