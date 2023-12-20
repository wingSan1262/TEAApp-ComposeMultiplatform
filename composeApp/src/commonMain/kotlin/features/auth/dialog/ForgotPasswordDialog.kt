@file:OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)

package features.auth.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.compose.colorPrimary
import base.compose.component.TeaModalBottomSheet
import base.compose.white
import base.extensions.ComposeValidatorForm
import base.extensions.ValidatorItemControl
import base.extensions.collectResourceState
import base.extensions.isEmailValid
import data.repo.model.request.auth.ReRequestOtp
import features.auth.component.TeaTextField
import features.auth.model.AuthModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForgotPasswordDialog (
    modalBottomSheetState: ModalBottomSheetState,
    onForgotPasswordSuccess: (email: String)->Unit,
){
    val scope = rememberCoroutineScope()
    TeaModalBottomSheet(
        modalBottomSheetState,
        backgroundColor = white,
    ){
        ForgotPasswordContent {
            scope.launch {
                onForgotPasswordSuccess(it)
                modalBottomSheetState.hide()
            }
        }
    }
}

@Composable
fun ForgotPasswordContent(
    onSuccess : (email: String)->Unit = {},
){
    val authModel : AuthModel = koinInject()
    val (isSuccess, isError, isLoading, _, _, message) = authModel.forgotPasswordData.collectResourceState()
    val (submitForm, formListState) = ComposeValidatorForm(
        ValidatorItemControl(errorChecker = { _, it ->
            if(it.isEmailValid{}) null else "Email tidak sesuai format"
        })
    ){ (email) ->
        authModel.forgotPasswordRequest(
            ReRequestOtp(email.value)
        )
    }
    val (emailState) = formListState.map { it.value }

    LaunchedEffect(isSuccess){ if(isSuccess) onSuccess(emailState.value) }

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = androidx.compose.ui.graphics.Color.White,
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )
            .padding(top =  16.dp, bottom = 40.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Permohonan lupa password", style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Text(
            "masukan email yang anda gunakan untuk akun anda", fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )

        TeaTextField(
            emailState.value,
            Icons.Default.Email,
            "Email yang anda gunakan",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            fontSize = 16.sp,
            error= if(isError) message?.message else emailState.errorMessage,
            onValueChange = { emailState.onValueChange(it) },
        )

        Button(
            colors = ButtonDefaults.buttonColors(colorPrimary),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                submitForm()
            }
        ) {
            Text(
                "Reset Password",
                Modifier.wrapContentSize(),
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                color = Color.White
            )
            if(isLoading)
                CircularProgressIndicator(
                    Modifier.size(10.dp),
                    color = Color.White, strokeWidth = 3.dp)
        }
    }
}