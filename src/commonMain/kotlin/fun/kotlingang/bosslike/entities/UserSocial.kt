package `fun`.kotlingang.bosslike.entities

import `fun`.kotlingang.bosslike.internal.interfaces.Identified
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class UserSocial(
    val id: Int,
    @SerialName("social_id")
    val socialId: Int,
    @SerialName("social_type")
    val socialType: Int,
    val name: String,
    val username: String,
    val url: String,
    val image: String,
    @Contextual
    val status: UserSocialStatus
)

enum class UserSocialStatus(override val id: Int) : Identified {
    /**
     * Социальная сеть активна.
     */
    ACTIVE(1),

    /**
     * Связь с социальной сетью потеряна.
     */
    LOST(0)
}

@Serializable
data class UserSocialDataBody(
    val socials: List<UserSocial>
)

@Serializable
data class AddedUserSocialBody(
    val message: String,
    val token: String,
    val social: UserSocial
)

@Serializable
data class ShowLikeBody(
    val message: String,
    val token: String,
    val url: String,
    @SerialName("service_type")
    val serviceType: String,
    @SerialName("task_type")
    val taskType: TaskType,
    val action: String,
    @SerialName("social_metadata")
    val socialMetadata: JsonObject
)