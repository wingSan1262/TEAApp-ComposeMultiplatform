package data.repo.model.response.healthcheck

import base.base_entity.TeaRemoteResponse
import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model.*
import domain.healthcheck.model.BaseLineModel
import domain.healthcheck.model.CodingModel
import domain.healthcheck.model.HistoryItemModel
import domain.healthcheck.model.InterpretationModel
import domain.healthcheck.model.OverviewLatestDataModel
import domain.healthcheck.model.OverviewLatestModel
import domain.healthcheck.model.OwnerModel
import domain.healthcheck.model.UsiaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestHealthResponse (
    override var status_code: Int,
    override var message: String,
    override var data: ListHealthResponse?
) : TeaRemoteResponse<ListHealthResponse> {
    fun toModel(): OverviewLatestModel {
        return OverviewLatestModel(
            status_code, message,
            OverviewLatestDataModel(
                listOf(
                    data?.systole?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Sistol"), unit = "Cek di Atm Sehat",
                        interpretation = InterpretationModel(display = "Cek di Atm Sehat")
                    ),
                    data?.diastole?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Diastol"), unit = "Cek di Atm Sehat",
                        interpretation = InterpretationModel(display = "Cek di Atm Sehat")
                    ),
                    data?.heartRate?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "BPM"), unit = "Cek di Atm Sehat"),
                    data?.bodyTemperature?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Suhu"), unit = "Cek di Atm Sehat"),
                    data?.bodyWeight?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Berat"), unit = "Cek di Atm Sehat"),
                    data?.bodyHeight?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Tinggi"), unit = "Cek di Atm Sehat"),
                    data?.oxygenSaturation?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "SpO2"), unit = "Cek di Atm Sehat"),
                    data?.bloodGlucose?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Gula Darah Sewaktu"), unit = "Cek di Atm Sehat",
                        interpretation = InterpretationModel(display = "Cek di Atm Sehat")
                    ),
                    data?.bloodCholesterol?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Kolestrol"), unit = "Cek di Atm Sehat"),
                    data?.uricAcid?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "Asam Urat"), unit = "Cek di Atm Sehat"),
                    data?.bmi?.toHistoryItemModel() ?: HistoryItemModel(
                        coding = CodingModel(display = "BMI"), unit = "Cek di Atm Sehat",
                        interpretation = InterpretationModel(display = "Cek di Atm Sehat")
                    )
                )
                , listOf(), listOf()
            )
        )
    }
}

@Serializable
data class Measurement(
    @SerialName("_id") val id: String? = null,
    @SerialName("value") val value: Double? = null,
    @SerialName("unit") val unit: Unit? = null,
    @SerialName("coding") val coding: Coding? = null,
    @SerialName("category") val category: Category? = null,
    @SerialName("base_line") val baseLine: BaseLine? = null,
    @SerialName("interpretation") val interpretation: Interpretation? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("time") val time: Long? = null
) {
    fun toHistoryItemModel(): HistoryItemModel {
        return HistoryItemModel(
            id ?: "", value ?: 0.0,
            unit?.display ?: "",
            (time?: 0) * 1000,
            baseLine?.toModel() ?: BaseLineModel(),
            interpretation?.toModel() ?: InterpretationModel(),
            coding?.toModel() ?: CodingModel(),
            OwnerModel(),
            "", "", UsiaModel()
        )
    }
}

@Serializable
data class Category(
    @SerialName("code") val code: String? = null,
    @SerialName("display") val display: String? = null,
    @SerialName("system") val system: String? = null
)

@Serializable
data class ListHealthResponse(
    @SerialName("systole") val systole: Measurement? = null,
    @SerialName("diastole") val diastole: Measurement? = null,
    @SerialName("hearth_rate") val heartRate: Measurement? = null,
    @SerialName("body_temperature") val bodyTemperature: Measurement? = null,
    @SerialName("body_weight") val bodyWeight: Measurement? = null,
    @SerialName("body_height") val bodyHeight: Measurement? = null,
    @SerialName("oxygen_saturation") val oxygenSaturation: Measurement? = null,
    @SerialName("blood_glucose") val bloodGlucose: Measurement? = null,
    @SerialName("blood_cholesterole") val bloodCholesterol: Measurement? = null,
    @SerialName("uric_acid") val uricAcid: Measurement? = null,
    @SerialName("bmi") val bmi: Measurement? = null
)