package `fun`.kotlingang.bosslike.internal.extensions

import `fun`.kotlingang.bosslike.entities.APIConfig
import io.ktor.client.request.*
import io.ktor.http.*

internal fun HttpRequestBuilder.putDefaults(apiConfig: APIConfig) {
    header("X-Api-Software-License-Key", apiConfig.softwareLicenseKey)
    header("X-Api-Device-Id", apiConfig.deviceId)
    header("X-Api-User-Agent", apiConfig.userAgent.toString())
    header("X-Api-Key", apiConfig.apiKey)
    contentType(ContentType.parse("application/json"))
}