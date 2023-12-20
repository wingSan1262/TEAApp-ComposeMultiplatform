package data.repo.model.response.profiles

import base.base_entity.TeaRemoteResponse
import domain.profiles.model.FamilyDataModel
import domain.profiles.model.FamilyListModel
import domain.profiles.model.FamilyModel
import domain.profiles.model.UsiaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class FamilyListResponse(
    override var status_code: Int,
    @SerialName("status") override var message: String,
    override var data: FamilyDataResponse?
) : TeaRemoteResponse<FamilyDataResponse> {
    fun toModel(): FamilyListModel {
        return FamilyListModel(
            status_code, message, data?.toModel() ?: FamilyDataModel()
        )
    }
}

@Serializable
data class FamilyDataResponse(
    val count: Long,
    val family: List<Family>,
) {
    fun toModel(): FamilyDataModel {
        return FamilyDataModel(
            count, family.map {
                it.toModel()
            }
        )
    }
}

@Serializable
data class Family(
    val id: String,
    @SerialName("nama_depan")
    val namaDepan: String,
    @SerialName("nama_belakang")
    val namaBelakang: String,
    @SerialName("tanggal_lahir")
    val tanggalLahir: String,
    @SerialName("tempat_lahir")
    val tempatLahir: String,
    val gender: String,
    @SerialName("hubungan_keluarga")
    val hubunganKeluarga: String,
    val status: Boolean,
    val usia: Usia,
) {
    fun toModel(): FamilyModel {
        return FamilyModel(
            id, namaDepan, namaBelakang,
            tanggalLahir, tempatLahir, gender,
            hubunganKeluarga, status, usia.toModel()
        )
    }
}

@Serializable
data class Usia(
    val tahun: Long,
    val bulan: Long,
    val hari: Long,
) {
    fun toModel(): UsiaModel {
        return UsiaModel(tahun, bulan, hari)
    }
}
