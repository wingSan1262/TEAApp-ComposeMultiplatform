package domain.healthcheck.model

import base.base_classes.BaseModel
import base.base_entity.TeaRemoteResponse

data class HealthHistoryModel(
    override var status_code: Int,
    override var message: String,
    override var data: List<HistoryItemModel>?,
) : TeaRemoteResponse<List<HistoryItemModel>>, BaseModel

data class HistoryItemModel(
    val id: String = "",
    val value: Double = 0.0,
    val unit: String = "",
    val time: Long = 0L,
    val baseLine: BaseLineModel = BaseLineModel(),
    val interpretation: InterpretationModel = InterpretationModel(),
    val coding: CodingModel = CodingModel(),
    val owner: OwnerModel = OwnerModel(),
    val namaPasien: String = "",
    var genderPasien: String = "",
    var usiaPasien: UsiaModel = UsiaModel(),
    var isUser: Boolean = true
) : BaseModel


data class UsiaModel(
    var tahun: Int? = null,
    var bulan: Int? = null,
    var hari: Int? = null
)

data class OwnerModel(
    val code: String? = null,
    val name: String? = null,
)


data class UnitModel(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
)

data class CodingModel(
    val code: String = "",
    val display: String = "",
    val system: String = "",
)

data class BaseLineModel(
    val min: Double? = null,
    val max: Double? = null,
    val neg3SD: Double? = null,
    val neg2SD: Double? = null,
    val neg1SD: Double? = null,
    val median: Double? = null,
    val pos1SD: Double? = null,
    val pos2SD: Double? = null,
    val pos3SD: Double? = null
)

data class InterpretationModel(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
)
