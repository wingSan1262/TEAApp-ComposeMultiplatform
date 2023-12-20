package data.repo.model.request.profiles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @SerialName("nama_depan")
    val namaDepan: String? = "",
    @SerialName("nama_belakang")
    val namaBelakang: String? = "",
    val gender: String? = "",
    @SerialName("tanggal_lahir")
    val tanggalLahir: String? = "",
    @SerialName("tempat_lahir")
    val tempatLahir: String? = "",
    val agama: String? = "",
    val pendidikan: String? = "",
    @SerialName("status_menikah")
    val statusMenikah: String? = "",
    val passport: String? = "",
    val suku: String? = "",
    @SerialName("warga_negara")
    val wargaNegara: String? = "",
    val nik: Long? = null,
)