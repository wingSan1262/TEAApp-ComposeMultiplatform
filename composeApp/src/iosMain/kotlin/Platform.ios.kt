import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val ktorEngine: HttpClientEngineFactory<HttpClientEngineConfig>
        get() = Darwin
}

class IOSPreferenceManager : PlatformPreference {
    override fun getString(key: String, defaultValue: String): String {
        val preferences = NSUserDefaults.standardUserDefaults
        return preferences.stringForKey(key) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        val preferences = NSUserDefaults.standardUserDefaults
        preferences.setObject(value, key)
        preferences.synchronize()
    }
}


actual fun getPlatform(): Platform = IOSPlatform()
actual fun getPreferenceManager(): PlatformPreference = IOSPreferenceManager()