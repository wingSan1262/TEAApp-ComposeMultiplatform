package base.base_classes

import dev.icerock.moko.resources.AssetResource
import tea.resources.MR


class Resources {
    companion object{
        object PlainText {
            val termConditionText: AssetResource = MR.assets.termcondition
        }
    }
}