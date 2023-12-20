package domain.auth.usecase

import base.base_classes.BaseUseCase
import data.local.LocalPreference
import data.repo.api.TeaAuthApi
import data.repo.model.request.auth.LoginRequest
import domain.auth.model.LoginModel


class LoginUseCase(
    val teaApi: TeaAuthApi,
    val sharedPrefApi: LocalPreference
) : BaseUseCase<LoginRequest, LoginModel>() {
    override fun setup(parameter: LoginRequest) {
        super.setup(parameter)
        execute {
            return@execute teaApi.login(
                parameter
            ).apply {
                sharedPrefApi.setBearerToken(this.token?.code ?: "")
            }
        }
    }
}

