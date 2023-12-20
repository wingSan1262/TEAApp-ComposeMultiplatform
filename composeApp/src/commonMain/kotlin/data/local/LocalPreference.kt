package data.local

import PlatformPreference


interface LocalPreference {
    fun getBearerToken():String
    fun setBearerToken(token: String)
}

class LocalPreferenceImpl(
    val platformPreference: PlatformPreference
): LocalPreference{
    companion object{
        const val BEARER_TOKEN = "BEARER_TOKEN"
    }

    override fun getBearerToken(): String {
        return platformPreference.getString(BEARER_TOKEN, "")
    }

    override fun setBearerToken(token: String) {
        platformPreference.putString(BEARER_TOKEN, token)
    }
}