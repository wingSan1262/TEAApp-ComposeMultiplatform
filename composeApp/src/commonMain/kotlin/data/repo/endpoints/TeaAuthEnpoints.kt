package data.repo.endpoints

object BaseUrls{
    const val BASE_URL_TEA = "https://dev.atm-sehat.com/"
}

object TeaAuthEndpoint {
    const val registerByEmail = BaseUrls.BASE_URL_TEA + "api/v1/user/registerByEmail"
    const val activateAccount = BaseUrls.BASE_URL_TEA + "api/v1/auth/aktifasi"
    const val reRequestOtp = BaseUrls.BASE_URL_TEA + "api/v1/auth/aktifasi"
    const val login = BaseUrls.BASE_URL_TEA + "api/v1/auth/login"
    const val loginWithGoogle = BaseUrls.BASE_URL_TEA + "api/v1/auth/loginGoogle"
    const val forgotPasswordRequest = BaseUrls.BASE_URL_TEA + "api/v1/auth/forgotpassword"
    const val resetPassword = BaseUrls.BASE_URL_TEA + "api/v1/auth/resetpassword"
    const val pairingToKitStation = BaseUrls.BASE_URL_TEA + "api/v1/kits/user/connect"
    const val logout = BaseUrls.BASE_URL_TEA + "api/v1/auth/logout"
}