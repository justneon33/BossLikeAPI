package `fun`.kotlingang.bosslike.entities

import `fun`.kotlingang.bosslike.internal.interfaces.Identified
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

enum class SocialType(override val id: Int) : Identified {
    VK(1),
    Facebook(2),
    Instagram(3),
    YouTube(4),
    Twitter(5),
    Odnoklassniki(7),
    Telegram(8),
    TikTok(9)
}

enum class TaskType(override val id: Int) : Identified {
    ALL(0), LIKE(1), REPOST(2), SUBSCRIBE(3), COMMENT(4), VOTE(8)
}

@Serializable
data class Task(
    val id: Int,
    val name: TaskName,
    val image: String,
    @SerialName("task_type")
    val taskType: TaskType,
    @SerialName("service_type")
    val socialType: SocialType,
    val price: TaskPrice
)

@Serializable
data class TaskName(
    val full: String,
    val action: String,
    @SerialName("short_action")
    val shortAction: String,
    @SerialName("object")
    val objectName: String
)

@Serializable
data class TaskPrice(
    val value: Int,
    val text: String
)

@Serializable
data class TasksBody(
    val items: List<Task>,
    val limit: Int
)

@Serializable
data class TaskDetails(
    val url: String,
    @Contextual
    @SerialName("task_type")
    val taskType: TaskType,
    @SerialName("service_type")
    val socialType: SocialType,
    val seconds: Int,
    val action: String,
    @SerialName("user_price")
    val userPrice: Int,
    val comment: String?,
    val answer: TaskAnswer,
    /**
     * Метаданные об объекте социальной сети
     */
    @SerialName("social_metadata")
    val metadata: JsonObject
)

@Serializable
data class TaskAnswer(
    /**
     * Порядковый номер варианта голосования
     */
    val value: Int,
    /**
     * Текстовое описание варианта голосования.
     */
    val text: String
)