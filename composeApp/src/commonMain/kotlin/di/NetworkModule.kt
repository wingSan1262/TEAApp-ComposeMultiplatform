package di

import PlatformPreference
import base.infra.httpClient
import data.local.LocalPreference
import data.local.LocalPreferenceImpl
import data.repo.ImageApi
import data.repo.ImageApiImpl
import data.repo.api.TeaAuthApi
import data.repo.api.TeaAuthApiImpl
import domain.auth.usecase.LoginUseCase
import domain.usecase.PhotoUseCase
import features.ImageList.ViewModel.ImageListModel
import features.auth.model.AuthModel
import getPreferenceManager
import org.koin.dsl.module

val networkModule = module {
    single {
        httpClient
    }
    single<ImageApi>{ ImageApiImpl(get()) }
    single<TeaAuthApi> { TeaAuthApiImpl(get())}
    single {getPreferenceManager()}
    single<LocalPreference>{ LocalPreferenceImpl(get()) }
}

