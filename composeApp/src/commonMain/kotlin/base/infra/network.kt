package base.infra

import getPlatform
import io.github.aakira.napier.Napier
import io.kamel.core.config.DefaultCacheSize
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.fileFetcher
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.imageBitmapDecoder
import io.kamel.image.config.imageVectorDecoder
import io.kamel.image.config.svgDecoder
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val httpClient by lazy {
    HttpClient(getPlatform().ktorEngine) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    allowSpecialFloatingPointValues = true
                    useArrayPolymorphism = true
                }
            )
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d("HTTP call $message")
                }
            }
            level = LogLevel.ALL
        }
    }
}

val customKamelConfig = KamelConfig {
    takeFrom(KamelConfig.Default)
    imageBitmapCacheSize = DefaultCacheSize
    imageBitmapDecoder()
    imageVectorDecoder()
    svgDecoder()
    fileFetcher()
    httpFetcher(httpClient)
}
