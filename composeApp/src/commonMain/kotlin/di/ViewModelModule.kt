package di

import features.ImageList.ViewModel.ImageListModel
import features.auth.model.AuthModel
import org.koin.dsl.module


val viewModelModule = module{
    single{ ImageListModel(get()) }
    factory { AuthModel(get(), get(), get(), get()) }
}