@file:OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)

package features.auth.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import base.base_classes.Resources
import base.compose.colorPrimary
import base.compose.component.TeaModalBottomSheet
import dev.icerock.moko.resources.compose.readTextAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TermAndConditionBottomSheet (
    modalBottomSheetState: ModalBottomSheetState,
    onPositive: ()->Unit,
    onNegative: ()->Unit,
){
    val textData = Resources.Companion.PlainText.termConditionText.readTextAsState()
    TeaModalBottomSheet(
        modalBottomSheetState,
        centerImage = "sign_in_logo.png",
        backgroundColor = colorPrimary,
        onNegative = onNegative,
        negativeButton = "Tolak",
        positiveButton = "Terima",
        onPositive = onPositive,
    ){

        Column(
            modifier = Modifier.fillMaxHeight(0.9f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(textData.value ?: "")
        }
    }
}