package `fun`.kotlingang.bosslike.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class SimpleMessage(open val message: String)

open class SocialMediaCheckedMessage(message: String, open val social: UserSocial) : SimpleMessage(message)

class SocialMediaCheckedTextSignMessage(
    override val message: String,
    override val social: UserSocial,
    @SerialName("verification_text")
    val verificationText: String
) : SocialMediaCheckedMessage(message, social)

class PhoneSentSuccessfullyMessage(message: String, val token: String) : SimpleMessage(message)

class TaskFinishedMessage(
    override val message: String,
    @SerialName("user_price")
    val userPrice: Int,
    val user: UserTaskFinishedBody
) : SimpleMessage(message)

@Serializable
data class UserTaskFinishedBody(
    val point: Int
)