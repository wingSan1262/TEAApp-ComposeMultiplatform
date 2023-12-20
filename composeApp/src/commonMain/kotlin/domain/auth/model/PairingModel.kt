package domain.auth.model

import base.base_entity.TeaRemoteResponse

data class PairingModel(
    override var status_code: Int,
    override var message: String,
    override var data: Any?
) : TeaRemoteResponse<Any?>