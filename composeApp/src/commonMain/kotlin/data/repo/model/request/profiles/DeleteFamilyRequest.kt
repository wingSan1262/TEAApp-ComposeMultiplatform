package data.repo.model.request.profiles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteFamilyRequest(
    @SerialName("id_user")
    val idUser: String? = "",
)