# BossLike Multiplatform API Library
Мультиплатформенная библиотека для взаимодействия с BossLike API (BotAPI).
## 🛠 Установка (Gradle)
### Groovy:
```groovy
repositories {
    maven { url 'https://maven.kotlingang.fun' }
}
```
И так же добавляем в вашем модуле:
```groovy
dependencies {
    implementation 'fun.kotlingang.bosslike.api:BossLike:0.0.1'
}
```
### Kotlin:
```kotlin
repositories {
    maven("https://maven.kotlingang.fun")
}
```
```kotlin
dependencies {
    implementation("fun.kotlingang.bosslike.api:BossLike:0.0.1")
}
```
#### Пример
К примеру, давайте создадим новый аккаунт.
```kotlin
val client = BossLikeAPIClient(APIConfig(
    apiKey = "API ключ пользователя",
    softwareLicenseKey = "Лицензионный ключ вашего софта",
    deviceId = UUID.randomUUID().toString(),
    userAgent = UserAgent("APPLICATION_NAME", "1.0", "Windows", "10", DeviceType.Desktop)
))
client.createUser("email@email.net", "p@\$\$w0rd").apply {
    handleSuccess {
        println("Успешно! Id пользователя: ${it.user.id}")
    }
    handleErrors {
        println("При вызове произошла ошибка! $it")
    }
}
```
