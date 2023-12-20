package data.repo.model.response.healthcheck

import base.base_entity.TeaRemoteResponse
import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model.ChartPointModel
import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model.HasilModel
import domain.healthcheck.model.BaseLineModel
import domain.healthcheck.model.InterpretationModel
import domain.healthcheck.model.UnitModel
import domain.healthcheck.model.UsiaModel
import kotlinx.serialization.Serializable

interface CommonChildChartMeasurement <ChartDataType> : TeaRemoteResponse<ChartDataType>

@Serializable
data class StuntingChartResponse(
    override var status_code: Int,
    override var message: String,
    override var data: StuntingData?
): CommonChildChartMeasurement<StuntingData>

@Serializable
data class GiziChartResponse(
    override var status_code: Int,
    override var message: String,
    override var data: GiziData?
): CommonChildChartMeasurement<GiziData>

private fun InterpretationModel.convertCodeToValue(): HasilModel {
    val codeValue = code?.replace("&lt", "")
        ?.replace("&gt", "")
        ?.replace(">", "")
        ?.replace("<", "")
        ?.replace("+", "")
        ?.replace("SD", "")
        ?.toDoubleOrNull() ?: 0.0
    return HasilModel(value = codeValue, unit = UnitModel("SD", "SD", "SD"))
}

@Serializable
data class GiziData(
    val status_gizi: List<StuntingResponse>? = null
) {
    fun toChartPointModel(): List<ChartPointModel>? {
        lateinit var pasien : PasienResponse
        lateinit var umurPasien : UsiaModel

        return status_gizi?.map { stuntingResponse ->
            pasien = stuntingResponse.pasien ?: PasienResponse()
            umurPasien = pasien.usia?.toModel() ?: UsiaModel()
            return@map ChartPointModel(
                id = stuntingResponse.id.orEmpty(),
                idPasien = stuntingResponse.id_pasien.orEmpty(),
                idPetugas = "",
                namaPasien = " ${pasien?.nama?.namaDepan} ${pasien?.nama?.namaBelakang}",
                umurPasienTahun = umurPasien?.tahun ?: 0,
                umurPasienBulan = umurPasien?.bulan ?: 0,
                time = (stuntingResponse.time ?: 0L) * 1000,
                hasil = stuntingResponse.interpretation?.toModel()?.convertCodeToValue() ?: HasilModel(),
                baseLine = stuntingResponse.base_line?.toModel() ?: BaseLineModel(),
                interpretationModel = stuntingResponse.interpretation?.toModel() ?: InterpretationModel()
            )
        }
    }
}

@Serializable
data class StuntingData(
    val stunting: List<StuntingResponse>? = null
) {
    fun toChartPointModel(): List<ChartPointModel>? {
        lateinit var pasien : PasienResponse
        lateinit var umurPasien : UsiaModel

        return stunting?.map { stuntingResponse ->
            pasien = stuntingResponse.pasien ?: PasienResponse()
            umurPasien = pasien.usia?.toModel() ?: UsiaModel()
            return@map ChartPointModel(
                id = stuntingResponse.id.orEmpty(),
                idPasien = stuntingResponse.id_pasien.orEmpty(),
                idPetugas = "",
                namaPasien = " ${pasien?.nama?.namaDepan} ${pasien?.nama?.namaBelakang}",
                umurPasienTahun = umurPasien?.tahun ?: 0,
                umurPasienBulan = umurPasien?.bulan ?: 0,
                time = (stuntingResponse.time ?: 0L) * 1000,
                hasil = stuntingResponse.interpretation?.toModel()?.convertCodeToValue() ?: HasilModel(),
                baseLine = stuntingResponse.base_line?.toModel() ?: BaseLineModel(),
                interpretationModel = stuntingResponse.interpretation?.toModel() ?: InterpretationModel()
            )
        }
    }
}

@Serializable
data class StuntingResponse(
    val id: String? = null,
    val id_pasien: String? = null,
    val pasien: PasienResponse? = null,
    val time: Long? = null,
    val hasil: Hasil? = null,
    val base_line: BaseLine? = null,
    val interpretation: Interpretation? = null
)

@Serializable
data class PasienResponse(
    val id: String? = null,
    val nama: Nama? = null,
    val gender: String? = null,
    val nik: Long? = null,
    val lahir: Lahir? = null,
    val usia: Usia? = null,
    val parent: Parent? = null
)



