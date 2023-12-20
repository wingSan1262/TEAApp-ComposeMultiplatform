package data.repo.model.response.healthcheck

import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.*
import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model.*
import domain.healthcheck.model.BaseLineModel
import domain.healthcheck.model.CodingModel
import domain.healthcheck.model.HealthHistoryModel
import domain.healthcheck.model.HistoryItemModel
import domain.healthcheck.model.InterpretationModel
import domain.healthcheck.model.OwnerModel
import domain.healthcheck.model.UnitModel
import domain.healthcheck.model.UsiaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class HealthHistoryResponse(
    @SerialName("status_code")
    val statusCode: Long,
    val message: String,
    val data: Data? = null,
) {
    fun toHistoryModel(): HealthHistoryModel {
        return HealthHistoryModel(
            statusCode.toInt(), message,
            data?.observations?.map {
                HistoryItemModel(
                    it.id ?: "",
                    it.hasil?.value ?: 0.0,
                    it.hasil?.unit?.display ?: "",
                    ((it.time ?: 0 ) * 1000),
                    it.baseLine?.toModel() ?: BaseLineModel(),
                    it.interpretation?.toModel() ?: InterpretationModel(),
                    it.coding?.toModel() ?: CodingModel(),
                    it.atmSehat?.owner?.toModel() ?: OwnerModel(),
                    (it.pasien?.nama?.namaDepan ?: "")
                            + " " +
                            (it.pasien?.nama?.namaBelakang ?: ""),
                    it.pasien?.gender ?: "Laki-laki",
                    it.pasien?.usia?.toModel() ?: UsiaModel(),
                    it.pasien?.parent == null
                )
            } ?: listOf()
        )
    }
}

@Serializable
data class Data(
    @SerialName("observations") var observations: ArrayList<Observations> = arrayListOf()
)

@Serializable
data class Observations(
    @SerialName("id") var id: String? = null,
    @SerialName("id_pasien") var idPasien: String? = null,
    @SerialName("pasien") var pasien: Pasien? = Pasien(),
    @SerialName("id_petugas") var idPetugas: String? = null,
    @SerialName("atm_sehat") var atmSehat: AtmSehat? = AtmSehat(),
    @SerialName("coding") var coding: Coding? = Coding(),
    @SerialName("time") var time: Long? = null,
    @SerialName("hasil") var hasil: Hasil? = Hasil(),
    @SerialName("base_line") var baseLine: BaseLine? = BaseLine(),
    @SerialName("interpretation") var interpretation: Interpretation? = Interpretation()
)
@Serializable
data class Pasien(
    @SerialName("id") var id: String? = null,
    @SerialName("nama") var nama: Nama? = Nama(),
    @SerialName("gender") var gender: String? = null,
    @SerialName("nik") var nik: Long? = null,
    @SerialName("lahir") var lahir: Lahir? = Lahir(),
    @SerialName("usia") var usia: Usia? = Usia(),
    @SerialName("parent") var parent: Parent? = Parent()
)

@Serializable
data class Nama(
    @SerialName("nama_depan") var namaDepan: String? = null,
    @SerialName("nama_belakang") var namaBelakang: String? = null
)

@Serializable
data class Parent(
    @SerialName("id_induk") var idInduk: String? = null,
    @SerialName("hubungan_keluarga") var hubunganKeluarga: String? = null,
    @SerialName("is_active") var isActive: Boolean? = null
)

@Serializable
data class Usia(
    @SerialName("tahun") var tahun: Int? = null,
    @SerialName("bulan") var bulan: Int? = null,
    @SerialName("hari") var hari: Int? = null
) {
    fun toModel(): UsiaModel {
        return UsiaModel(tahun, bulan, hari)
    }
}

@Serializable
data class Lahir(
    @SerialName("tempat") var tempat: String? = null,
    @SerialName("tanggal") var tanggal: String? = null
)

@Serializable
data class Unit(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
) {
    fun toModel(): UnitModel {
        return UnitModel(code, display, system)
    }
}

@Serializable
data class AtmSehat(
    val code: String? = null,
    val name: String? = null,
    val owner: Owner? = null,
)

@Serializable
data class Owner(
    val code: String? = null,
    val name: String? = null,
) {
    fun toModel(): OwnerModel {
        return OwnerModel(code, name)
    }
}

@Serializable
data class Coding(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
) {
    fun toModel(): CodingModel {
        return CodingModel(code ?: "", display ?: "", system ?: "")
    }
}

@Serializable
data class BaseLine(
    val min: Double? = null,
    val max: Double? = null,
    @SerialName("-3SD") val neg3SD: Double? = null,
    @SerialName("-2SD") val neg2SD: Double? = null,
    @SerialName("-1SD") val neg1SD: Double? = null,
    val median: Double? = null,
    @SerialName("+1SD")val pos1SD: Double? = null,
    @SerialName("+2SD") val pos2SD: Double? = null,
    @SerialName("+3SD") val pos3SD: Double? = null
) {
    fun toModel(): BaseLineModel {
        return BaseLineModel(
            min, max, neg3SD, neg2SD, neg1SD, median, pos1SD, pos2SD, pos3SD
        )
    }
}

@Serializable
data class Interpretation(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
) {
    fun toModel(): InterpretationModel {
        var newCode = code?.replace("<", "&lt")
        newCode = newCode?.replace(">", "&gt")
        return InterpretationModel(
            newCode ?: "", display, system)
    }
}

@Serializable
data class Link(
    val url: String?? = null,
    val label: String? = null,
    val active: Boolean? = null,
)
