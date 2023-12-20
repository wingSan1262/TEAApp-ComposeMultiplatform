package domain.auth.model

import base.base_entity.TeaRemoteResponse


data class ActivateAccountModel(
    override var status_code: Int,
    override var message: String,
    override var data : Any? = null,
    var errorrs    : ActivtatingAccountErrorrs? = ActivtatingAccountErrorrs()
): TeaRemoteResponse<Any?>

data class ActivtatingAccountErrorrs (
    var email : ArrayList<String> = arrayListOf(),
    var otp   : ArrayList<String> = arrayListOf()
)