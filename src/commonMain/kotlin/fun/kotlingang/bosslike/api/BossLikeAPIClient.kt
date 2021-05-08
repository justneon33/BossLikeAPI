package `fun`.kotlingang.bosslike.api

import `fun`.kotlingang.bosslike.entities.*
import `fun`.kotlingang.bosslike.internal.extensions.putDefaults
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class BossLikeAPIClient(
    var config: APIConfig,
    private val baseUrl: String = "https://api-public.bosslike.ru/v1/bots"
) {

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        expectSuccess = false
    }

    /**
     * Регистрация пользователя.
     * @param email - почта пользователя.
     * @param password - пароль пользователя.
     * @param refererId - рефералка.
     */
    suspend fun createUser(email: String, password: String, refererId: Int? = null) =
        client.post<Response<BossLikeRegisterBody>>("$baseUrl/users/create/") {
            putDefaults(config)
            body = buildJsonObject {
                put("email", email)
                put("password", password)
                put("password_repeat", password)
                put("referer_id", refererId)
            }
        }

    /**
     * Получение данных пользователя.
     * @return [BossLikeUser] с данными пользователя.
     */
    suspend fun getUser() = client.get<Response<BossLikeUser>>("$baseUrl/users/me") {
        putDefaults(config)
    }

    /**
     * Получение социальный сетей пользователя.
     * @param type - тип социальной сети, что будет получена.
     * @return [UserSocialDataBody] с социальными сетями, что привязанны к аккаунту пользователя.
     */
    suspend fun getUserSocials(type: SocialType) =
        client.get<Response<UserSocialDataBody>>("$baseUrl/users/me/socials/") {
            putDefaults(config)
            parameter("type", type.id)
        }

    /**
     * Открепляет социальную сеть пользователя.
     * @param type - тип социальной сети, что будет отвязана.
     */
    suspend fun removeUserSocial(type: SocialType) =
        client.delete<Response<SimpleMessage>>("$baseUrl/users/me/social/") {
            putDefaults(config)
            parameter("type", type.id)
        }


    /**
     * Добавляет социальную сеть с помощью лайка.
     * @param url - Ссылка на аккаунт или @nickname.
     * @param type - тип социальной сети (тут могут быть только Вконтакте, Инстаграм или тикток).
     * @return [AddedUserSocialBody] - сообщение об успешной проверке профиля и token для доступа к следующим этапам.
     */
    suspend fun addSocialMediaByLike(url: String, type: SocialType) =
        client.post<Response<AddedUserSocialBody>>("$baseUrl/users/me/social/auth/like") {
            putDefaults(config)
            body = buildJsonObject {
                put("url", url)
                put("type", type.id)
            }
        }

    /**
     * Получает задание, с помощью которого авторизация будет валидирована.
     * @param token - токен доступа к заданию, полученный с [addSocialMediaByLike].
     * @return [ShowLikeBody] с данными о задании для выполнения.
     */
    suspend fun getLikeTaskForAddingSocial(token: String) =
        client.get<Response<ShowLikeBody>>("$baseUrl/users/me/social/auth/like/show-like/") {
            putDefaults(config)
            parameter("token", token)
        }

    /**
     * Проверяет задание которое должно было выполнено ([getLikeTaskForAddingSocial]).
     * @param token - токен доступа к заданию, полученный с [addSocialMediaByLike].
     * @return [ShowLikeBody]
     */
    suspend fun verifyLikeTaskForAddingSocial(token: String) =
        client.get<Response<SocialMediaCheckedMessage>>("$baseUrl/users/me/social/auth/like/check-like/") {
            putDefaults(config)
            parameter("token", token)
        }

    /**
     * Запускает добавление социальной сети по номеру (только Телеграмм).
     * @param phoneNumber - Номер телефона.
     * @param socialMediaType - Тип социальной сети (только Телеграмм обслуживается в данный момент).
     * @return [PhoneSentSuccessfullyMessage] с данными для [следующего этапа](verifyPhoneAuthorization).
     */
    suspend fun addSocialMediaByPhone(phoneNumber: String, socialMediaType: SocialType = SocialType.Telegram) =
        client.post<Response<PhoneSentSuccessfullyMessage>>("$baseUrl/users/me/social/auth/phone") {
            putDefaults(config)
            body = buildJsonObject {
                put("phone_number", phoneNumber)
                put("service_type", socialMediaType.id)
            }
        }

    /**
     * Отправляет код для авторизации.
     * @param token - токен доступа (должен быть взят с предыдущего этапа [addSocialMediaByLike]).
     * @param code - код который пришёл на номер телефона или клиент.
     * @param socialMediaType - Тип социальной сети (только Телеграмм обслуживается в данный момент).
     */
    suspend fun verifyPhoneAuthorization(
        token: String,
        code: String,
        socialMediaType: SocialType = SocialType.Telegram
    ) = client.post<Response<SocialMediaCheckedMessage>>("$baseUrl/users/me/social/auth/phone") {
        putDefaults(config)
        body = buildJsonObject {
            put("token", token)
            put("code", code)
            put("service_type", socialMediaType.id)
        }
    }

    /**
     * Привязать социальную сеть к аккаунту пользователя
     * с помощью подписи в описании профиля - проверка профиля.
     * @param url - Ссылка на аккаунт или @nickname.
     * @param socialMediaType - Тип социальной сети (в данный момент только YouTube и Twitter поддерживаются).
     */
    suspend fun addSocialMediaByTextSign(url: String, socialMediaType: SocialType) =
        client.post<Response<SocialMediaCheckedTextSignMessage>>(
            "$baseUrl/users/me/social/auth/text-sign/check-profile/"
        ) {
            putDefaults(config)
            body = buildJsonObject {
                put("url", url)
                put("type", socialMediaType.id)
            }
        }

    /**
     * Проверяет было ли добавлено в описание ключевое слово с [addSocialMediaByTextSign].
     * @param token - Токен доступа, полученный в [addSocialMediaByTextSign].
     * @return [AddedUserSocialBody] с данными о социальной сети.
     */
    suspend fun checkTextSign(token: String) = client.get<Response<AddedUserSocialBody>>(
        "$baseUrl/users/me/social/auth/text-sign/check-textsign"
    ) {
        putDefaults(config)
        body = buildJsonObject {
            put("token", token)
        }
    }

    /**
     * Получает список доступных заданий для выполнения.
     * @param socialType - тип социальной сети.
     * @param taskType - тип задания.
     */
    suspend fun getTasks(socialType: SocialType, taskType: TaskType) =
        client.get<Response<TasksBody>>("$baseUrl/tasks/") {
            putDefaults(config)
            body = buildJsonObject {
                put("service_type", socialType.id)
                put("task_type", taskType.id)
            }
        }

    /**
     * Инициализирует выполнение задания и возвращает информацию для выполнения.
     * @param taskId - Идентификатор задания.
     * @return [TaskDetails] с данными о заданиию
     */
    suspend fun takeTask(taskId: Int) = client.get<Response<TaskDetails>>("$baseUrl/tasks/do") {
        putDefaults(config)
        parameter("id", taskId)
    }

    /**
     * Проверяет задание на выполнение.
     * @param taskId - Идентификатор задания.
     * @return [TaskDetails] с данными о заданиию
     */
    suspend fun verifyTask(taskId: Int) = client.get<Response<TaskFinishedMessage>>("$baseUrl/tasks/check") {
        putDefaults(config)
        parameter("id", taskId)
    }


}