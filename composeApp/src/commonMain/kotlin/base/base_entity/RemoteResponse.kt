package base.base_entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface TeaRemoteResponse<Data> {
    var status_code: Int
    var message: String
    var data: Data?
}

@Serializable
data class CommonResponse(
    override var status_code: Int,
    override var message: String,
    @SerialName("asdw131adw")
    override var data: String? = null
) : TeaRemoteResponse<String?>