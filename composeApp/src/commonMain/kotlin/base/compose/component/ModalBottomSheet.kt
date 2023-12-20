package base.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import base.compose.black
import base.compose.colorPrimary
import base.compose.white
import features.auth.component.TeaCommonButton
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun TeaModalBottomSheet(
    modalSheetState: ModalBottomSheetState,
    backgroundColor: Color = white,
    centerImage: String = "",
    leadingImage: ImageVector? = null,
    positiveButton: String = "",
    negativeButton: String = "",
    onPositive: ()->Unit={},
    onNegative: ()->Unit={},
    content: @Composable ()->Unit,
){
    val scope = rememberCoroutineScope()
    return ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column{
                Row(
                    Modifier.fillMaxWidth().height(50.dp)
                        .background(backgroundColor),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){

                    if(leadingImage != null)
                        Image(
                            leadingImage,
                            contentDescription = "background",
                            modifier = Modifier
                                .height(16.dp).padding(horizontal = 16.dp)
                                .clickable { scope.launch {
                                    modalSheetState.hide()
                                }},
                            contentScale = ContentScale.FillBounds
                        )
                    else
                        Spacer(Modifier
                            .height(16.dp).padding(horizontal = 16.dp)
                            .clickable { scope.launch {
                                modalSheetState.hide()
                            }})

                    Box(
                        Modifier.fillMaxWidth(0.5f).fillMaxHeight(),
                        contentAlignment = Alignment.Center,
                    ){
                        if(centerImage.isNotEmpty())
                            Image(
                                painterResource("sign_in_logo.png"),
                                contentDescription = "background",
                                modifier = Modifier.padding(start= 8.dp, top= 8.dp, bottom = 8.dp).height(32.dp),
                            )
                    }

                    Image(
                        Icons.Default.Close,
                        contentDescription = "background",
                        modifier = Modifier.padding(end = 16.dp)
                            .clickable { scope.launch {
                                modalSheetState.hide()
                            }},
                        contentScale = ContentScale.FillBounds
                    )
                }
                content()
                Row(Modifier.padding(horizontal = 16.dp)){
                    if(negativeButton.isNotEmpty())
                        TeaCommonButton(
                            buttonText = negativeButton, onClick = {
                                scope.launch { modalSheetState.hide() }
                                onNegative()
                            },
                            activeColor = white,
                            textColor = black,
                            modifier = Modifier.weight(1f).padding(8.dp),
                        )
                    if(positiveButton.isNotEmpty())
                        TeaCommonButton(
                            buttonText = positiveButton, onClick = {
                                scope.launch { modalSheetState.hide() }
                                onPositive()
                            },
                            modifier = Modifier.weight(1f).padding(8.dp),
                        )
                }
            }

        }
    ){}
}