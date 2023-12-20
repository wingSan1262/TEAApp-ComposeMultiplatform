package features.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.compose.component.DialogBottomSheet
import base.compose.component.TeaDialog
import base.extensions.ComposeValidatorForm
import base.extensions.ValidatorItemControl
import base.extensions.collectResourceState
import base.extensions.isEmailValid
import base.extensions.isPasswordValid
import base.extensions.rememberTeaBottomSheet
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.repo.model.request.auth.LoginRequest
import features.auth.component.TeaCommonButton
import features.auth.component.TeaTextField
import features.auth.dialog.ForgotPasswordDialog
import features.auth.dialog.ResetPasswordDialog
import features.auth.model.AuthModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        LoginActivity()
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun LoginActivity() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    val authModel = koinInject<AuthModel>()
    val (
        isLoginSuccess, isLoginError, _, _, _, loginMessage
    ) = authModel.loginData.collectResourceState()

    val forgotBottomSheet = rememberTeaBottomSheet()
    val resetBottomSheet = rememberTeaBottomSheet()
    val dialogBottomSheet = rememberTeaBottomSheet()

    val (submitForm, formListState) = ComposeValidatorForm(
        ValidatorItemControl(errorChecker = { _, it ->
            if(it.isEmailValid{}) null else "Email tidak sesuai format"
        }),
        ValidatorItemControl(errorChecker = { _, it ->
            if(it.isPasswordValid{}) null else "Password tidak boleh kosong"
        })
    ){ (email, password) ->
        authModel.doLogin(LoginRequest(email.value, password.value))
    }
    val (emailState, passwordState) = formListState.map { it.value }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource("bg_sign.png"),
            contentDescription = "background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource("sign_in_logo.png"),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 64.dp)
            )

            Text(
                text = "Login",
                color = Color.White,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.dp)
            ) {
                TeaTextField(
                    emailState.value,
                    null,
                    "Email",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    onValueChange = { emailState.onValueChange(it) },
                    error = emailState.errorMessage,
                ){  }

                TeaTextField(
                    passwordState.value,
                    null,
                    "Password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    fontSize = 16.sp,
                    error= passwordState.errorMessage,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    isPassword = true,
                    onValueChange = { passwordState.onValueChange(it) },
                ){}

                TeaCommonButton(
                    buttonText = "Login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, start = 56.dp, end = 56.dp),
                ){
                    submitForm()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.clickable {
                            navigator.push(CreateAccountScreen)
                        }
                    )

                    Text(
                        text = "Forget Password",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.clickable {
                            scope.launch { forgotBottomSheet.show() }
                        }
                    )
                }

                TeaCommonButton(
                    onClick = {
                    },
                    activeColor = Color.White,
                    textColor = Black,
                    buttonText = "Login with google",
                    leadingIcon = "google_logo.png",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, start = 56.dp, end = 56.dp),
                )
            }
        }
    }

    ForgotPasswordDialog(forgotBottomSheet){
        scope.launch { resetBottomSheet.show() }
    }

    ResetPasswordDialog(resetBottomSheet){
        scope.launch { dialogBottomSheet.show() }
    }

    DialogBottomSheet(
        dialogBottomSheet, "Reset password berhasil",
        "silahkan login dengan password baru anda",
        "ok"
    )

    if(isLoginError){
        TeaDialog("Login Error", loginMessage?.message ?: "", true)
    }


}
