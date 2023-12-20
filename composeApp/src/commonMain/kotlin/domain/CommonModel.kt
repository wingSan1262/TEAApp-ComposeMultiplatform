package domain

import base.base_entity.TeaRemoteResponse

class CommonModel(
    override var status_code: Int,
    override var message : String,
    override var data: Any?,
): TeaRemoteResponse<Any?>