package `fun`.kotlingang.bosslike.entities

import kotlinx.serialization.Serializable

@Serializable
data class APIError(val code: Int, val message: String)
