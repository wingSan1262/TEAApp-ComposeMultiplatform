@file:OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)

package base.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.base_classes.Resources
import base.compose.component.TeaModalBottomSheet
import base.compose.white
import dev.icerock.moko.resources.compose.readTextAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogBottomSheet (
    modalBottomSheetState: ModalBottomSheetState,
    title: String = "",
    message: String = "",
    positiveButton: String = "",
    negativeButton: String = "",
    onPositive: ()->Unit = {},
    onNegative: ()->Unit = {},
){
    TeaModalBottomSheet(
        modalBottomSheetState,
        onNegative = onNegative,
        negativeButton = negativeButton,
        positiveButton = positiveButton,
        onPositive = onPositive,
        backgroundColor = white
    ){
        Column(Modifier.padding(horizontal = 16.dp)){
            Text(title, fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            Text(
                message,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp))
        }
    }
}