package features.auth.model

import cafe.adriel.voyager.core.model.ScreenModel
import data.repo.model.request.auth.ActivateAccountRequest
import data.repo.model.request.auth.LoginRequest
import data.repo.model.request.auth.ReRequestOtp
import data.repo.model.request.auth.RegisterRequestModel
import data.repo.model.request.auth.ResetPasswordRequest
import domain.auth.usecase.ActivateUserUseCase
import domain.auth.usecase.ForgotPassworUseCase
import domain.auth.usecase.LoginUseCase
import domain.auth.usecase.ReRequestOtpUseCase
import domain.auth.usecase.RegisterUserUseCase
import domain.auth.usecase.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class AuthModel(
    val loginUseCase: LoginUseCase,
    val registerUserUseCase: RegisterUserUseCase,
    val forgotPassworUseCase: ForgotPassworUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase,
): ScreenModel {

    fun doLogin(
        req: LoginRequest
    ) = loginUseCase.setup(req)
    val loginData = loginUseCase.currentData

    val isTermAccepted = MutableStateFlow(false)
    fun acceptTerm(){isTermAccepted.value = true}

    val registerUserData = registerUserUseCase.currentData
    fun registerUser(
        req: RegisterRequestModel
    ) = registerUserUseCase.setup(req)

    val forgotPasswordData = forgotPassworUseCase.currentData
    fun forgotPasswordRequest(
        req: ReRequestOtp
    ) = forgotPassworUseCase.setup(req)

    val resetPasswordData = resetPasswordUseCase.currentData
    fun resetPasswordRequest(
        req: ResetPasswordRequest
    ) = resetPasswordUseCase.setup(req)

}