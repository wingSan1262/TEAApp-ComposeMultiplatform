package com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model

import base.base_entity.TeaRemoteResponse
import domain.healthcheck.model.BaseLineModel
import domain.healthcheck.model.CodingModel
import domain.healthcheck.model.InterpretationModel
import domain.healthcheck.model.UnitModel

data class ChartModel(
    override var status_code: Int = 200,
    override var message: String = "",
    override var data: List<Pair<String, List<ChartPointModel>>>? = listOf(),
) : TeaRemoteResponse<List<Pair<String, List<ChartPointModel>>>>

fun List<Pair<String, List<ChartPointModel>>>.isExisting(): Boolean {
    forEach {
        if (it.second.isEmpty()) return false
    }
    return true
}

data class ChartPointModel(
    var id: String = "",
    var idPasien: String = "",
    var idPetugas: String = "",
    var namaPasien : String = "",
    var umurPasienTahun : Int = 0,
    var umurPasienBulan : Int = 0,
    var coding: CodingModel = CodingModel(),
    var time: Long = 0,
    var hasil: HasilModel = HasilModel(),
    var baseLine: BaseLineModel = BaseLineModel(),
    var interpretationModel: InterpretationModel = InterpretationModel()
)

data class HasilModel(
    var value : Double = 0.0,
    var unit  : UnitModel = UnitModel()
)

data class SelectedChartModel(
    val data : ChartModel,
    val title : String
)