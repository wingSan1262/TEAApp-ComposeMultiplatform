package domain.auth.model

import base.base_entity.TeaRemoteResponse

data class LoginModel(
    override var status_code: Int,
    override var message: String,
    override var data : Any? = null,
    var token : UserTokenModel? = null,
    var time  : Int?   = null
) : TeaRemoteResponse<Any?>

data class UserTokenModel (
    var name   : String,
    var code   : String,
    var type   : String,
    var userId : String
)