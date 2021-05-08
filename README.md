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
## 🚴 Примеры использования
#### Выполнение заданий:
```kotlin
val client = BossLikeClient("TEST-API-KEY")
// получаем все привязанные соц.сети
val socials = client.getSocials()
// проверяем авторизован ли телеграмм
if(socials.data?.any {
    it.socialType == SocialNetworkType.TELEGRAM.id } != true) {
    throw RuntimeException("Телега не привязана")
}
// получаем задания телеграмм
val telegramTask = client.getTasks(SocialNetworkType.TELEGRAM, TaskType.ALL).get(0)
// инициализируем задание
val initTask = client.initializeTask(telegramTasks.data!!.id)
// выполняем задание с помощью своих средств
// проверяем задание
client.checkTask(telegramTasks.data!!.id).onSuccess {
    // задание завершено
}.onError { status, errors ->
    // логируем ошибки
    println("#$status: ${errors.toString()}")
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
