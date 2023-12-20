import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import base.infra.customKamelConfig
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.networkModule
import di.useCaseModule
import di.viewModelModule
import features.ImageList.view.ImageListScreen
import features.auth.screens.SplashScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.kamel.image.config.LocalKamelConfig
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinApplication(
        application = {
            modules(
                networkModule, useCaseModule, viewModelModule
            )
        }
    ){
        Napier.base(DebugAntilog())
        MaterialTheme {
            CompositionLocalProvider(
                LocalKamelConfig provides
                        customKamelConfig
            ) {
                Navigator(SplashScreen){
                    SlideTransition(it)
                }
            }
        }
    }
}
