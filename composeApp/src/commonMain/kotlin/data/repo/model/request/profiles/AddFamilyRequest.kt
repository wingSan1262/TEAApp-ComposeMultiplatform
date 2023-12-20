package data.repo.model.request.profiles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddFamilyRequest(
    @SerialName("nama_depan")
    val namaDepan: String = "",
    @SerialName("nama_belakang")
    val namaBelakang: String = "",
    val gender: String = "male",
    @SerialName("tanggal_lahir")
    val tanggalLahir: String = "",
    @SerialName("tempat_lahir")
    val tempatLahir: String = "",
    @SerialName("hubungan_keluarga")
    val hubunganKeluarga: String = "Anak",
    @SerialName("nik")
    val nik: Long? = 0
) {
    fun isValid(): Boolean {
        return namaDepan.isNotEmpty() && namaBelakang.isNotEmpty() && gender.isNotEmpty()
                &&tanggalLahir.isNotEmpty() && tempatLahir.isNotEmpty() && hubunganKeluarga.isNotEmpty()
    }
}