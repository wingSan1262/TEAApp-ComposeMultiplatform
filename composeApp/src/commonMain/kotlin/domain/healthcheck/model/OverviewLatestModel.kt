package domain.healthcheck.model

import base.base_classes.BaseModel
import base.base_entity.TeaRemoteResponse

data class OverviewLatestModel(
    override var status_code: Int,
    override var message: String,
    override var data: OverviewLatestDataModel?,
) : TeaRemoteResponse<OverviewLatestDataModel>, BaseModel

data class OverviewLatestDataModel(
    val healthLatestList : List<HistoryItemModel>,
    val stuntingLatestList : List<ChildMeasurementModel>,
    val giziLatestList : List<ChildMeasurementModel>
)

data class ChildMeasurementModel(
    val id: String = "",
    val value: Double = 0.0,
    val unit: String = "",
    val time: Long = 0,
    val namaPasienAnak : String = "",
    val umur : String = "",
    val status : String = "",
    val code : String = "",
    val displayPengukuran : String,
) : BaseModel

