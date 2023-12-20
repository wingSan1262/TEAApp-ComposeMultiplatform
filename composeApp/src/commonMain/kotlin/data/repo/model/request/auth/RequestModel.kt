package data.repo.model.request.auth

import base.extensions.isEmailValid
import base.extensions.isPasswordValid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestModel(
    @SerialName("email"         ) var email: String= "",
    @SerialName("password"      ) var password: String= "",
)

fun RegisterRequestModel.validateData(): ArrayList<String> {
    val listError = arrayListOf<String>()
    if(!this.email.isEmailValid{ })
        listError.add("email")
    if(!this.password.isPasswordValid{ })
        listError.add("password")
    return listError
}
@Serializable
data class ActivateAccountRequest(
    @SerialName("email" ) var email : String,
    @SerialName("otp"   ) var otp   : Int
)

@Serializable
data class ReRequestOtp (
    @SerialName("email"         ) var email        : String? = null,
    @SerialName("nomor_telepon" ) var nomorTelepon : String? = null
)

@Serializable
data class LoginRequest (
    @SerialName("username" ) var username : String? = null,
    @SerialName("password" ) var password : String? = null
){
    fun updateUsername(input: String){username = input}
    fun updatePassword(input: String){password = input}
}

@Serializable
data class LoginWithGoogleRequest (@SerialName("access_token") val accessToken : String)

@Serializable
data class ResetPasswordRequest (
    @SerialName("otp"                   ) var otp                  : Int?    = null,
    @SerialName("username"              ) var username             : String? = null,
    @SerialName("password"              ) var password             : String? = null,
    @SerialName("password_confirmation" ) var passwordConfirmation : String? = null
)

@Serializable
data class PairRequest (
    @SerialName("code_kit" ) var codeKit : String? = null
)