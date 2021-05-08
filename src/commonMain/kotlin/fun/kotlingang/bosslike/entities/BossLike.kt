package `fun`.kotlingang.bosslike.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossLikeRegisterBody(
    val token: BossLikeToken,
    val user: BossLikeUser
)

@Serializable
data class BossLikeToken(
    val key: String,
    @SerialName("expire_at")
    val expireAt: Int,
    val type: String
)

@Serializable
data class BossLikeUser(
    val id: Int,
    val login: String,
    val email: String,
    @SerialName("email_confirm")
    val isEmailConfirmed: Boolean,
    val name: String,
    val point: Int,
    val money: Int,
    val status: Int
)
