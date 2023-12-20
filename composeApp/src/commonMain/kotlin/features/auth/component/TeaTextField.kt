package features.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TeaTextField(
    text: String = "",
    icon: ImageVector? = null,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    error: String? = null,
    fontSize: TextUnit = 12.sp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isPassword: Boolean = false,
    onValueChange: ((String) -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier
        .background(Color.White,shape = RoundedCornerShape(8.dp))){
        Row(
            Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null)
                Icon(icon, contentDescription = null, Modifier.padding(start= 8.dp))
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(start = 8.dp)
                    .clickable { onClick() },
                value = text,
                enabled = onValueChange != null,
                textStyle = TextStyle(fontSize = fontSize),
                keyboardOptions = keyboardOptions,
                onValueChange = {
                    if (onValueChange != null) onValueChange(it) else {
                    }
                },
                visualTransformation = if(isPassword && !passwordVisible)
                    PasswordVisualTransformation() else
                        VisualTransformation.None,
                decorationBox = { innerTextField ->
                    Column {
                        Row(
                            Modifier
                        ) {
                            if (text.isEmpty()) {
                                Text(placeholder, color = Color.Gray, fontSize = fontSize)
                            }
                            innerTextField()
                        }
                    }
                },
            )
            if(isPassword)
                Image(
                    painter = painterResource(if(passwordVisible)
                        "visible.png" else "hide.png"),
                    contentDescription = "background",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { passwordVisible = !passwordVisible },
                    contentScale = ContentScale.FillBounds
                )
        }
        error?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
            )
        } ?: kotlin.run {
            Spacer(Modifier.height(16.dp).padding(start = 8.dp, bottom = 4.dp))
        }
    }
}