package di

import domain.auth.usecase.ActivateUserUseCase
import domain.auth.usecase.ForgotPassworUseCase
import domain.auth.usecase.LoginUseCase
import domain.auth.usecase.ReRequestOtpUseCase
import domain.auth.usecase.RegisterUserUseCase
import domain.auth.usecase.ResetPasswordUseCase
import domain.usecase.PhotoUseCase
import org.koin.dsl.module


val useCaseModule = module{
    factory{ PhotoUseCase(get()) }
    factory { LoginUseCase(get(), get()) }
    factory { RegisterUserUseCase(get()) }
    factory { ForgotPassworUseCase(get()) }
    factory { ResetPasswordUseCase(get()) }
}
