package data.repo.api

import base.base_entity.CommonResponse
import base.infra.doPost
import base.infra.doTeaPost
import base.infra.doTeaPut
import data.repo.endpoints.TeaAuthEndpoint
import data.repo.model.request.auth.ActivateAccountRequest
import data.repo.model.request.auth.LoginRequest
import data.repo.model.request.auth.PairRequest
import data.repo.model.request.auth.ReRequestOtp
import data.repo.model.request.auth.RegisterRequestModel
import data.repo.model.request.auth.ResetPasswordRequest
import data.repo.model.response.auth.ActivateAccountResponse
import data.repo.model.response.auth.LoginResponse
import data.repo.model.response.auth.ResetPasswordResponse
import domain.CommonModel
import domain.auth.model.ActivateAccountModel
import domain.auth.model.LoginModel
import domain.auth.model.PairingModel
import domain.auth.model.ResetPasswordModel
import io.ktor.client.HttpClient

interface TeaAuthApi {
    suspend fun registerUser(req : RegisterRequestModel) : LoginModel
    suspend fun activateUser(req : ActivateAccountRequest) : ActivateAccountModel
    suspend fun reRequestOtp(req : ReRequestOtp) : ActivateAccountModel
    suspend fun login(req : LoginRequest) : LoginModel
//    suspend fun loginWithGoogle(req : LoginWithGoogleRequest) : LoginResponse
    suspend fun forgotPassword(req : ReRequestOtp) : CommonResponse
    suspend fun resetPassword(req : ResetPasswordRequest) : CommonResponse
    suspend fun pairingKitByQr(req: PairRequest) : CommonResponse
//    suspend fun logout() : CommonModel
}

class TeaAuthApiImpl(
    val httpClient: HttpClient
) : TeaAuthApi {
    override suspend fun registerUser(req: RegisterRequestModel): LoginModel {
        return httpClient
            .doTeaPost<LoginResponse, RegisterRequestModel>(
                TeaAuthEndpoint.registerByEmail, req).toModel()
    }

    override suspend fun activateUser(req: ActivateAccountRequest): ActivateAccountModel {
        return httpClient
            .doTeaPut<ActivateAccountResponse, ActivateAccountRequest>(
                TeaAuthEndpoint.activateAccount, req).toModel()
    }

    override suspend fun reRequestOtp(req: ReRequestOtp): ActivateAccountModel {
        return httpClient
            .doTeaPost<ActivateAccountResponse, ReRequestOtp>(
                TeaAuthEndpoint.registerByEmail, req).toModel()
    }

    override suspend fun login(req: LoginRequest): LoginModel {
        return httpClient.doPost<LoginResponse, LoginRequest>(
            TeaAuthEndpoint.login,
            req
        ).toModel()
    }
//
//    override suspend fun loginWithGoogle(req: LoginWithGoogleRequest): LoginResponse {
//        return teaRetrofitInterface.loginWithGoogle(req)
//    }

    override suspend fun forgotPassword(req: ReRequestOtp): CommonResponse {
        return httpClient
            .doTeaPost<CommonResponse, ReRequestOtp>(
                TeaAuthEndpoint.forgotPasswordRequest, req)
    }

    override suspend fun resetPassword(req: ResetPasswordRequest): CommonResponse {
        return httpClient
            .doTeaPut<CommonResponse, ResetPasswordRequest>(
                TeaAuthEndpoint.resetPassword, req)
    }

    override suspend fun pairingKitByQr(req: PairRequest): CommonResponse {
        return httpClient
            .doTeaPost<CommonResponse, PairRequest>(
                TeaAuthEndpoint.pairingToKitStation, req)
    }

}