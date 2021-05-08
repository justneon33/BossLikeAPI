package `fun`.kotlingang.bosslike.entities

data class APIConfig(
    /**
     * API ключ пользователя. Получить можно [тут](https://bosslike.ru/user/settings/#user_apikey_form).
     */
    var apiKey: String,
    /**
     * Лицензионный ключ вашего софта
     */
    var softwareLicenseKey: String,
    /**
     * Значение в формате UUID v4, например 0d3e9a11-a1ef-47f2-9bb1-b9d8736021ab.
     *
     * Это уникальный идентификатор устройства на котором работает ваш софт,
     * его можно сгенерировать при установке и хранить на машине пользователя.
     * Для каждой копии программы должен формироваться свой уникальный device id.
     */
    var deviceId: String,
    /**
     * Мета-информация о клиенте: Имя приложения, версия, ОС,
     * тип девайса, имя девайса.
     */
    var userAgent: UserAgent
)

data class UserAgent(
    /**
     * Имя приложения.
     */
    var appName: String,
    /**
     * Версия приложения.
     */
    var appVersion: String,
    /**
     * Операционная система пользователя, может быть опущено, если нет возможности определить.
     */
    var OS: String = "",
    /**
     * Версия операционной системы пользователя, может быть опущено, если нет возможности определить.
     */
    var OSVersion: String = "",
    /**
     * Тип девайса пользователя, может быть опущено, если нет возможности определить.
     */
    var deviceType: DeviceType? = null,
    /**
     * Имя устройства, может быть опущено, если нет возможности определить.
     */
    var deviceName: String = ""
) {
    override fun toString(): String {
        return "app=$appName;app_version=$appVersion;" +
            "os=$OS;os_version=$OSVersion;device_type=${deviceType ?: ""};" +
            "device_name=$deviceName"
    }
}

enum class DeviceType {
    Desktop, Laptop, Mobile, Server
}