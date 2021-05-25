[![Hits-of-Code](https://hitsofcode.com/github/y9neon/BossLikeAPI)](https://hitsofcode.com/github/y9neon/BossLikeAPI/view)
[![Last Version](https://badge.kotlingang.fun/maven/fun/kotlingang/bosslike/api/BossLike)](https://maven.kotlingang.fun/fun/kotlingang/bosslike/api/BossLike)

# BossLike Multiplatform API Library
Мультиплатформенная библиотека для взаимодействия с BossLike API (BotAPI).
## 🛠 Установка (Gradle)
`$version` - должно быть взято со значка выше.
### Groovy:
```groovy
repositories {
    maven { url 'https://maven.kotlingang.fun' }
}
```
И так же добавляем в вашем модуле:
```groovy
dependencies {
    implementation 'fun.kotlingang.bosslike.api:BossLike:$version'
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
    implementation("fun.kotlingang.bosslike.api:BossLike:$version")
}
```
#### Пример
```kotlin
val client = BossLikeAPIClient(APIConfig(/* ... */))
client.createUser(email ="me@me.me", password = "sure").runOnSuccess {
    println("Success! User's token: ${it.token.key}")
}.runOnError { errors: List<APIError> ->
    errors.forEach(::println)
}

// получаем задания
client.getTasks(SocialType.Telegram, TaskType.SUBSCRIBE).runOnSuccess {
    val taskId = it.items.first().id
    client.takeTask(taskId).runOnSuccess { details: TaskDetails ->
        println("Задание по ссылке ${details.url} выполняется")
        // выполняем своими силами
        /* ... */
        // проверяем
        println(client.verifyTask(taskId).success)
    }.runOnError {
        println("Ошибка при инициализации выполнения")
    }
}.runOnError {
    println("Ошибка при получении заданий!")
}
```
