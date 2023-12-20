package domain.auth.usecase

import base.base_classes.BaseUseCase
import base.base_entity.CommonResponse
import data.local.LocalPreference
import data.repo.api.TeaAuthApi
import data.repo.model.request.auth.ActivateAccountRequest
import data.repo.model.request.auth.LoginRequest
import data.repo.model.request.auth.PairRequest
import data.repo.model.request.auth.ReRequestOtp
import data.repo.model.request.auth.RegisterRequestModel
import data.repo.model.request.auth.ResetPasswordRequest
import data.repo.model.response.auth.ActivateAccountResponse
import domain.auth.model.ActivateAccountModel
import domain.auth.model.LoginModel
import domain.auth.model.ResetPasswordModel


class RegisterUserUseCase(
    val teaApi: TeaAuthApi,
) : BaseUseCase<RegisterRequestModel, LoginModel>() {
    override fun setup(parameter: RegisterRequestModel) {
        super.setup(parameter)
        execute {
            return@execute teaApi.registerUser(
                parameter
            )
        }
    }
}

class ActivateUserUseCase(
    val teaApi: TeaAuthApi,
) : BaseUseCase<ActivateAccountRequest, ActivateAccountModel>() {
    override fun setup(parameter: ActivateAccountRequest) {
        super.setup(parameter)
        execute {
            return@execute teaApi.activateUser(
                parameter
            )
        }
    }
}

class ReRequestOtpUseCase(
    val teaApi: TeaAuthApi,
) : BaseUseCase<ReRequestOtp, ActivateAccountModel>() {
    override fun setup(parameter: ReRequestOtp) {
        super.setup(parameter)
        execute {
            return@execute teaApi.reRequestOtp(
                parameter
            )
        }
    }
}

class ForgotPassworUseCase(
    val teaApi: TeaAuthApi,
) : BaseUseCase<ReRequestOtp, CommonResponse>() {
    override fun setup(parameter: ReRequestOtp) {
        super.setup(parameter)
        execute {
            return@execute teaApi.forgotPassword(
                parameter
            )
        }
    }
}

class ResetPasswordUseCase(
    val teaApi: TeaAuthApi,
) : BaseUseCase<ResetPasswordRequest, CommonResponse>() {
    override fun setup(parameter: ResetPasswordRequest) {
        super.setup(parameter)
        execute {
            return@execute teaApi.resetPassword(
                parameter
            )
        }
    }
}

class PairAtmSehatUseCase(
    val teaApi: TeaAuthApi,
) : BaseUseCase<PairRequest, CommonResponse>() {
    override fun setup(parameter: PairRequest) {
        super.setup(parameter)
        execute {
            return@execute teaApi.pairingKitByQr(
                parameter
            )
        }
    }
}

