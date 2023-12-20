package di

import data.repo.ImageApi
import data.repo.ImageApiImpl
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.named

fun initKoin(appDeclaration: KoinAppDeclaration= {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(
            networkModule, useCaseModule
        )
    }
}
