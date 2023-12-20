package data.repo.model.response.auth

import base.base_entity.TeaRemoteResponse
import domain.auth.model.ActivateAccountModel
import domain.auth.model.ActivtatingAccountErrorrs
import domain.auth.model.ErrorResetModel
import domain.auth.model.LoginModel
import domain.auth.model.ResetPasswordDataModel
import domain.auth.model.ResetPasswordModel
import domain.auth.model.UserTokenModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivateAccountResponse(
    override var status_code: Int,
    override var message: String,
    override var data : String? = null,
    @SerialName("errorrs"     ) var errorrs    : Errorrs? = Errorrs()
): TeaRemoteResponse<String?> {
    fun toModel(): ActivateAccountModel {
        return ActivateAccountModel(
            status_code, message, data, errorrs?.toModel()
        )
    }
}

@Serializable
data class Errorrs (
    @SerialName("email" ) var email : ArrayList<String> = arrayListOf(),
    @SerialName("otp"   ) var otp   : ArrayList<String> = arrayListOf()
) {
    fun toModel(): ActivtatingAccountErrorrs? {
        return ActivtatingAccountErrorrs(
            email, otp
        )
    }
}

@Serializable
data class LoginResponse(
    override var status_code: Int,
    override var message: String,
    override var data : String? = null,
    @SerialName("token" ) var token : UserToken? = null,
    @SerialName("time"  ) var time  : Int?   = null
) : TeaRemoteResponse<String?> {
    fun toModel(): LoginModel {
        return LoginModel(
            status_code, message, data, token?.toModel(), time
        )
    }
}

@Serializable
data class UserToken (
    @SerialName("name"    ) var name   : String,
    @SerialName("code"    ) var code   : String,
    @SerialName("type"    ) var type   : String,
    @SerialName("user_id" ) var userId : String
) {
    fun toModel(): UserTokenModel {
        return UserTokenModel(
            name, code, type, userId
        )
    }
}


@Serializable
data class ResetPasswordResponse (
    @SerialName("status_code" ) override var status_code : Int,
    @SerialName("message"     ) override var message    : String,
    @SerialName("data ")        override var data: ResetPasswordData?,
    ): TeaRemoteResponse<ResetPasswordData> {
    fun toModel(): ResetPasswordModel {
        return ResetPasswordModel(
            status_code, message, data?.toModel()
        )
    }
}

@Serializable
data class ResetPasswordData (
    @SerialName("errors" ) var errors : ResetPasswordErrors? = ResetPasswordErrors()
) {
    fun toModel(): ResetPasswordDataModel {
        return ResetPasswordDataModel(errors?.toModel())
    }
}

@Serializable
data class ResetPasswordErrors (
    @SerialName("otp"      ) var otp      : ArrayList<String> = arrayListOf(),
    @SerialName("username" ) var username : ArrayList<String> = arrayListOf(),
    @SerialName("password" ) var password : ArrayList<String> = arrayListOf()
) {
    fun toModel(): ErrorResetModel {
        return ErrorResetModel(otp, username, password)
    }
}