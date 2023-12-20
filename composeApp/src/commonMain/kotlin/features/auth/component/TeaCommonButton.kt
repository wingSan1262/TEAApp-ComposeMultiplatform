package features.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import base.compose.colorPrimary
import base.compose.white
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TeaCommonButton(
    isLoading: Boolean = false,
    isDisabled: Boolean = false,
    activeColor: Color = colorPrimary,
    loadingColor : Color = white,
    textColor: Color = white,
    leadingIcon : String = "",
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            if (!isLoading && !isDisabled) {
                onClick()
            }
        },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(if (isDisabled) Color.Gray else activeColor),
        enabled = !isDisabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if(leadingIcon.isNotEmpty()){
                Image(
                    painter = painterResource(leadingIcon),
                    contentDescription = "background",
                    modifier = Modifier
                        .height(20.dp).aspectRatio(1f),
                    contentScale = ContentScale.FillBounds
                )
            }
            Text(
                text = buttonText,
                color = if (isDisabled) Color.Gray else textColor
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = loadingColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}