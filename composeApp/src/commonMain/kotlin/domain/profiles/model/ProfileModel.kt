package domain.profiles.model

import base.base_entity.TeaRemoteResponse
import data.repo.model.request.profiles.UpdateProfileRequest
import kotlinx.serialization.SerialName

data class ProfileModel(
    override var status_code: Int,
    override var message: String,
    override var data: ProfileDataModel? = ProfileDataModel()
) : TeaRemoteResponse<ProfileDataModel>

data class ProfileDataModel(
    val id: String = "",
    val namaDepan: String = "",
    val namaBelakang: String = "",
    val gender: String = "",
    val tempatLahir: String = "",
    val tanggalLahir: String = "",
    val username: String = "",
    val nik: Long = 0,
    val email: String = "",
    @SerialName("nomor_telepon")
    val nomorTelepon: String = "",
    @SerialName("status_pernikahan")
    val statusPernikahan: String = "",
    @SerialName("pendidikan") var pendidikan: String = "",
    @SerialName("passport") var passport: String = "",
    @SerialName("suku") var suku: String = "",
    @SerialName("warga_negara") var wargaNegara: String = "",
    val agama: String = "",
    val imageProfileUrl: String = "",
    @SerialName("is_email_verified") val isEmailVerified: Boolean? = null,
    @SerialName("is_phone_number_verified") val isPhoneNumberVerified: Boolean? = null,
) {
    val areMandatoryFieldsNotEmpty get() = nik > 0 &&
            gender.isNotEmpty() &&
            namaDepan.isNotEmpty() &&
            tanggalLahir.isNotEmpty()

    val isAccountVerified get() = isEmailVerified == true && areMandatoryFieldsNotEmpty

    fun toProfileUpdateRequest(): UpdateProfileRequest {
        return UpdateProfileRequest(
            namaDepan.toValueOrNull(), namaBelakang.toValueOrNull(),
            gender.toValueOrNull(), tanggalLahir.toValueOrNull(),
            tempatLahir.toValueOrNull(), agama.toValueOrNull(), pendidikan.toValueOrNull(),
            statusPernikahan.toValueOrNull(), passport.toValueOrNull(), suku.toValueOrNull(),
            wargaNegara.toValueOrNull(), nik
        )
    }
}

fun String.toValueOrNull(): String? {
    return this.ifEmpty { null }
}
