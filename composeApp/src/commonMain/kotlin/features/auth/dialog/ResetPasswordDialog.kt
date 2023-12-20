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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.base_classes.Resources
import base.compose.colorPrimary
import base.compose.colorPrimaryDark
import base.compose.component.TeaModalBottomSheet
import base.compose.white
import base.extensions.ComposeValidatorForm
import base.extensions.ValidatorItemControl
import base.extensions.collectResourceState
import base.extensions.isEmailValid
import base.extensions.isOtpValid
import base.extensions.isPasswordKonfirmValid
import base.extensions.isPasswordValid
import data.repo.model.request.auth.LoginRequest
import data.repo.model.request.auth.ReRequestOtp
import data.repo.model.request.auth.ResetPasswordRequest
import dev.icerock.moko.resources.compose.readTextAsState
import features.auth.component.TeaTextField
import features.auth.model.AuthModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResetPasswordDialog (
    modalBottomSheetState: ModalBottomSheetState,
    onResetSuccess: ()->Unit,
){
    val scope = rememberCoroutineScope()
    TeaModalBottomSheet(
        modalBottomSheetState,
        backgroundColor = white,
    ){
        ResetPasswordContent {
            scope.launch {
                onResetSuccess()
                modalBottomSheetState.hide()
            }
        }
    }
}

@Composable
fun ResetPasswordContent(
    onSuccess : ()->Unit = {},
){
    val authModel : AuthModel = koinInject()
    val (
        isSuccess, isError, isLoading, _, _, message
    ) = authModel.resetPasswordData.collectResourceState()

    val (submitForm, formState) = ComposeValidatorForm(
        ValidatorItemControl(errorChecker = { _, it ->
            if (it.isEmailValid{}) null else "Email harus sesuai format" }),
        ValidatorItemControl(errorChecker = { _, it ->
            if (it.isOtpValid{}) null else "OTP harus sesuai format" }),
        ValidatorItemControl(errorChecker = { _, it ->
            if (it.isPasswordValid{}) null else "Password harus 6 digit atau lebih"}),
        ValidatorItemControl(errorChecker = { (_, _,password), it ->
            if (it.isPasswordKonfirmValid(password.value){})  null else "Password Konfirmasi Harus Sama" })
    ){ (email, otp, password, passwordConfirm)->
        authModel.resetPasswordRequest(
            ResetPasswordRequest(
                otp.value.toInt(), email.value, password.value, passwordConfirm.value
            )
        )
    }
    val (
        emailState, otpState,
        passwordState, passwordConfirm
    ) = formState.map { it.value }

    LaunchedEffect(isSuccess){ if(isSuccess) onSuccess() }

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = androidx.compose.ui.graphics.Color.White,
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )
            .padding(top = 16.dp, bottom = 40.dp, start = 30.dp, end = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Reset password akun", style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Text(
            "Mohon lengkapi data dibawah ini. Otp dapat diperoleh di kotak masuk email anda", fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp), textAlign = TextAlign.Center
        )

        TeaTextField(
            emailState.value,
            null,
            "Email yang anda gunakan",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            fontSize = 16.sp,
            error= if(isError) message?.message else emailState.errorMessage,
            onValueChange = { emailState.onValueChange(it) },
        )

        TeaTextField(
            otpState.value,
            null,
            "OTP dari email anda",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            fontSize = 16.sp,
            error= if(isError) message?.message else otpState.errorMessage,
            onValueChange = { otpState.onValueChange(it) },
        )

        TeaTextField(
            passwordState.value,
            null,
            "Password baru",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            fontSize = 16.sp,
            error= if(isError) message?.message else passwordState.errorMessage,
            onValueChange = { passwordState.onValueChange(it) },
        )

        TeaTextField(
            passwordConfirm.value,
            null,
            "konfirmasi password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            fontSize = 16.sp,
            error= if(isError) message?.message else passwordConfirm.errorMessage,
            onValueChange = { passwordConfirm.onValueChange(it) },
        )

        Button(
            colors = ButtonDefaults.buttonColors(colorPrimary),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                if(isLoading) return@Button
                submitForm()
            }
        ) {
            Text(
                "Reset Password",
                Modifier.wrapContentSize(),
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                color = androidx.compose.ui.graphics.Color.White
            )
            if(isLoading)
                CircularProgressIndicator(
                    Modifier.size(10.dp),
                    color = Color.White, strokeWidth = 3.dp)
        }
    }
}