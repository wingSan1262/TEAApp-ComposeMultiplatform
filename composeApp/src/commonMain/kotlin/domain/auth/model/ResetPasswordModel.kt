package domain.auth.model

import base.base_entity.TeaRemoteResponse
import kotlinx.serialization.SerialName

data class ResetPasswordModel (
    override var status_code : Int,
    override var message    : String,
    override var data: ResetPasswordDataModel?,
): TeaRemoteResponse<ResetPasswordDataModel>

data class ResetPasswordDataModel (
    var errors : ErrorResetModel? = ErrorResetModel()
)

data class ErrorResetModel (
    var otp      : ArrayList<String> = arrayListOf(),
    var username : ArrayList<String> = arrayListOf(),
    var password : ArrayList<String> = arrayListOf()
)