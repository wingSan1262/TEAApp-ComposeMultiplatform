import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

interface Platform {
    val name: String
    val ktorEngine: HttpClientEngineFactory<HttpClientEngineConfig>
}

interface PlatformPreference {
    fun getString(key: String, defaultValue: String): String
    fun putString(key: String, value: String)
}

expect fun getPlatform(): Platform
expect fun getPreferenceManager(): PlatformPreference