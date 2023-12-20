package domain.profiles.model

import base.base_entity.TeaRemoteResponse
import data.repo.model.request.profiles.DeleteFamilyRequest
import kotlinx.serialization.SerialName

class FamilyListModel(
    override var status_code: Int,
    override var message: String,
    override var data: FamilyDataModel?
) : TeaRemoteResponse<FamilyDataModel>

data class FamilyDataModel(
    val count: Long = 0,
    val family: List<FamilyModel> = listOf(),
)

data class FamilyModel(
    val id: String = "",
    val namaDepan: String = "",
    @SerialName("nama_belakang")
    val namaBelakang: String = "",
    @SerialName("tanggal_lahir")
    val tanggalLahir: String = "",
    @SerialName("tempat_lahir")
    val tempatLahir: String = "",
    val gender: String = "",
    @SerialName("hubungan_keluarga")
    val hubunganKeluarga: String = "Anak",
    val status: Boolean = false,
    val usia: UsiaModel = UsiaModel(),
) {
    fun toDeleteFamilyMember() : DeleteFamilyRequest {
        return DeleteFamilyRequest(
            id.toValueOrNull()
        )
    }
}

data class UsiaModel(
    val tahun: Long = 0,
    val bulan: Long = 0,
    val hari: Long = 0,
)
