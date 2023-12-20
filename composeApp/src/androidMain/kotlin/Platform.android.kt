import android.content.Context
import android.os.Build
import androidx.core.content.edit
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO
import telesehat.tea.app.MyApplication

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val ktorEngine: HttpClientEngineFactory<HttpClientEngineConfig> =
        CIO
}

class AndroidPreferenceManager : PlatformPreference {
    val appInstance by lazy {
        MyApplication.instance
    }
    private val sharedPreferences by lazy {
        appInstance.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: ""
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value); apply() }
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun getPreferenceManager() : PlatformPreference = AndroidPreferenceManager()