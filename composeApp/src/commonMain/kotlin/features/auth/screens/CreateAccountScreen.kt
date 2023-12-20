package features.auth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.compose.colorPrimary
import base.compose.white
import base.extensions.ComposeValidatorForm
import base.extensions.ValidatorItemControl
import base.extensions.collectResourceState
import base.extensions.go
import base.extensions.isEmailValid
import base.extensions.isPasswordKonfirmValid
import base.extensions.isPasswordValid
import base.extensions.rememberTeaBottomSheet
import cafe.adriel.voyager.core.screen.Screen
import data.repo.model.request.auth.LoginRequest
import data.repo.model.request.auth.RegisterRequestModel
import features.auth.dialog.TermAndConditionBottomSheet
import features.auth.component.TeaCommonButton
import features.auth.component.TeaTextField
import features.auth.component.PartialClickableText
import features.auth.model.AuthModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

object CreateAccountScreen : Screen {
    @Composable
    override fun Content() {
        CreateAccountScreenContent()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateAccountScreenContent() {
    val authModel = koinInject<AuthModel>()
    val isTermAccepted by authModel.isTermAccepted.collectAsState()
    val (
        isSuccessRegister, isErrorRegister,
        isLoadingRegister, _, _, messageRegister
    ) = authModel.registerUserData.collectResourceState()

    val (
        isSuccessLogin, isErrorLogin, isLoadingLogin, _, _, messageLoading
    ) = authModel.loginData.collectResourceState()

    val termAndConditionSheet = rememberTeaBottomSheet()
    val scope = rememberCoroutineScope()

    val (submitRegister, formState) = ComposeValidatorForm(
        ValidatorItemControl(errorChecker = { _, it ->
            if (it.isEmailValid{}) null else "Email harus sesuai format" }),
        ValidatorItemControl(errorChecker = { _, it ->
            if (it.isPasswordValid{}) null else "Password harus 6 digit atau lebih"}),
        ValidatorItemControl(errorChecker = { (_,password), it ->
            if (it.isPasswordKonfirmValid(password.value){})  null else "Password Konfirmasi Harus Sama" })
    ){ (email, password) ->
        if(isTermAccepted)
            authModel.registerUser(RegisterRequestModel(email.value, password.value))
        else
            scope.go{ termAndConditionSheet.show() }
    }
    val ( emailState, passwordState, passwordConfirmState ) = formState.map { it.value }

    LaunchedEffect(isSuccessRegister, isSuccessLogin){
        if(isSuccessRegister)
            authModel.doLogin(
                LoginRequest(emailState.value, passwordState.value))

        if(isSuccessLogin){}
            // TODO navigate next screen
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 30.dp)
    ) {
        Text(
            text = "Buat Akun",
            color = Color.Gray,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        )

        // Email EditText
        TeaTextField(
            emailState.value,
            Icons.Default.Email,
            "Email",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            onValueChange = { emailState.onValueChange(it) },
            error = if(isErrorRegister) messageRegister?.message else emailState.errorMessage,
        )

        TeaTextField(
            passwordState.value,
            Icons.Default.Lock,
            "Password",
            fontSize = 16.sp,
            isPassword = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            onValueChange = { passwordState.onValueChange(it) },
            error = if(isErrorRegister) messageRegister?.message else passwordState.errorMessage,
        )

        TeaTextField(
            passwordConfirmState.value,
            Icons.Default.Lock,
            "Konfirm Password",
            fontSize = 16.sp,
            isPassword = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            onValueChange = { passwordConfirmState.onValueChange(it) },
            error = if(isErrorRegister) messageRegister?.message else passwordConfirmState.errorMessage,
        )

        TeaCommonButton(
            isLoadingRegister || isLoadingLogin,
            onClick = { submitRegister() },
            activeColor = colorPrimary,
            textColor = white,
            buttonText = "Daftar",
            modifier = Modifier
                .fillMaxWidth().height(40.dp)
                .padding(top = 6.dp, start = 56.dp, end = 56.dp),
        )

        Text(
            "Atau",
            Modifier.fillMaxWidth().padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        TeaCommonButton(
            onClick = {
            },
            activeColor = Color.White,
            textColor = Black,
            buttonText = "Login with google",
            leadingIcon = "google_logo.png",
            modifier = Modifier
                .fillMaxWidth().height(40.dp)
                .padding(top = 6.dp, start = 56.dp, end = 56.dp),
        )
        PartialClickableText(
            "Dengan menekan daftar anda setuju dengan ",
            "Syarat dan Ketentuan",
            Modifier.fillMaxWidth().padding(vertical = 16.dp),
        ){
            scope.launch { termAndConditionSheet.show() }
        }
        Spacer(Modifier.padding(vertical = 16.dp))
        PartialClickableText(
            "Sudah punya akun? ",
            "Login",
            Modifier.fillMaxWidth().padding(vertical = 16.dp),
        ){

        }
    }

    TermAndConditionBottomSheet(termAndConditionSheet,
        { authModel.acceptTerm() },{})



}
