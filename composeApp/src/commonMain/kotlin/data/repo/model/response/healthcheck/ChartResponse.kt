package data.repo.model.response.healthcheck

import base.base_entity.TeaRemoteResponse
import domain.healthcheck.model.BaseLineModel
import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model.ChartModel
import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model.ChartPointModel
import domain.healthcheck.model.CodingModel
import com.telehealtheasyaccess.mobileapp.domain.migration_be.healthcheck.model.HasilModel
import domain.healthcheck.model.UnitModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChartResponse(
    override var status_code: Int,
    override var message: String,
    override var data: HashMap<String, ArrayList<DataItem>>?
) : TeaRemoteResponse<HashMap<String, ArrayList<DataItem>>> {
    fun toModel() : ChartModel {
        return ChartModel(
            status_code, message,
            data?.map { Pair(it.key, it.value.map { it.toModel() }) }
        )
    }
}

@Serializable
data class DataItem (
    @SerialName("id"             ) var id             : String?         = null,
    @SerialName("id_pasien"      ) var idPasien       : String?         = null,
    @SerialName("id_petugas"     ) var idPetugas      : String?         = null,
    @SerialName("atm_sehat"      ) var atmSehat       : AtmSehat?       = AtmSehat(),
    @SerialName("coding"         ) var coding         : Coding?         = Coding(),
    @SerialName("time"           ) var time           : Long?            = null,
    @SerialName("hasil"          ) var hasil          : Hasil?          = Hasil(),
    @SerialName("base_line"      ) var baseLine       : BaseLine?       = BaseLine(),
    @SerialName("interpretation" ) var interpretation : Interpretation? = Interpretation()
) {
    fun toModel() : ChartPointModel {
        return ChartPointModel(
            id ?: "", idPasien ?: "", idPetugas  ?: "",
            coding = coding?.toModel() ?: CodingModel(),
            time =  (time ?: 0L) * 1000, hasil = hasil?.toModel() ?: HasilModel(),
            baseLine = baseLine?.toModel() ?: BaseLineModel()
        )
    }
}

@Serializable
data class Hasil (
    @SerialName("value" ) var value : Double?  = null,
    @SerialName("unit"  ) var unit  : Unit? = Unit()
) {
    fun toModel(): HasilModel {
        return HasilModel(value ?: 0.0, unit?.toModel() ?: UnitModel())
    }
}