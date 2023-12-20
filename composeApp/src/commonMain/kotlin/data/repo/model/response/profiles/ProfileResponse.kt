package data.repo.model.response.profiles

import base.base_entity.TeaRemoteResponse
import domain.profiles.model.ProfileDataModel
import domain.profiles.model.ProfileModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class ProfileResponse (
    override var status_code: Int,
    override var message: String,
    @SerialName("data") override var data: ProfileData? = ProfileData()
) : TeaRemoteResponse<ProfileData> {
    fun toModel(): ProfileModel {
        return ProfileModel(
            status_code, message,
            data?.user?.toModel() ?: ProfileDataModel()
        )
    }
}
@Serializable
data class ProfileData(
    val user: UserData = UserData(),
)

@Serializable
data class UserData(
    @SerialName("id"            ) var id           : String?     = null,
    @SerialName("gelar"         ) var gelar        : Gelar?      = Gelar(),
    @SerialName("nama_depan"    ) var namaDepan    : String?     = null,
    @SerialName("nama_belakang" ) var namaBelakang : String?     = null,
    @SerialName("gender"        ) var gender       : String?     = null,
    @SerialName("lahir"         ) var lahir        : Lahir?      = Lahir(),
    @SerialName("username"      ) var username     : String?     = null,
    @SerialName("nik"           ) var nik          : Long?        = null,
    @SerialName("email"         ) var email        : String?     = null,
    @SerialName("nomor_telepon" ) var nomorTelepon : String?     = null,
    @SerialName("agama"         ) var agama        : Agama?      = Agama(),
    @SerialName("pendidikan"    ) var pendidikan   : Pendidikan? = Pendidikan(),
    @SerialName("suku"          ) var suku         : String?     = null,
    @SerialName("warga_negara"  ) var wargaNegara  : String?     = null,
    @SerialName("passport"      ) var passport     : String?     = null,
    @SerialName("foto" ) var foto : PhotoResponse? = PhotoResponse(),
    @SerialName("is_email_verified" ) val isEmailVerified : Boolean? = null,
    @SerialName("is_phone_number_verified" ) val isPhoneNumberVerified : Boolean? = null,
    var status_menikah : StatusMenikah? = StatusMenikah()
) {
    fun toModel(): ProfileDataModel {
        return ProfileDataModel(
            id ?: "", namaDepan ?: "", namaBelakang ?: "", gender ?: "male",
            lahir?.tempat ?: "", lahir?.tanggal ?: "",
            username ?: "", nik ?: 0, email ?: "",
            nomorTelepon ?: "",
            try{
                Json.decodeFromString<StatusMenikah>(status_menikah.toString()).code ?: ""
               } catch (e:Throwable){""},
            pendidikan?.kode ?: "", passport ?: "", suku ?: "",
            wargaNegara ?: "", agama?.name ?: "", foto?.url ?: "",
            isEmailVerified ?: false, isEmailVerified ?: false
        )
    }
}

@Serializable
data class PhotoResponse (
    @SerialName("url"       ) var url       : String? = null,
    @SerialName("name"      ) var name      : String? = null,
    @SerialName("extention" ) var extention : String? = null,
    @SerialName("mimeType"  ) var mimeType  : String? = null,
    @SerialName("size"      ) var size      : Int?    = null
)
@Serializable
data class Pendidikan (
    @SerialName("kode"       ) var kode       : String? = null,
    @SerialName("pendidikan" ) var pendidikan : String? = null
)

data class Nama (
    @SerialName("nama_depan"    ) var namaDepan    : String? = null,
    @SerialName("nama_belakang" ) var namaBelakang : String? = null
)

@Serializable
data class Gelar (
    @SerialName("gelar_depan"    ) var gelarDepan    : String? = null,
    @SerialName("gelar_belakang" ) var gelarBelakang : String? = null
)

@Serializable
data class Agama (
    @SerialName("id"   ) var id   : String? = null,
    @SerialName("name" ) var name : String? = null
)

@Serializable
data class StatusMenikah (
    @SerialName("code"    ) var code    : String? = null,
    @SerialName("display" ) var display : String? = null
)

@Serializable
data class Lahir(
    val tempat: String = "",
    val tanggal: String = "",
)