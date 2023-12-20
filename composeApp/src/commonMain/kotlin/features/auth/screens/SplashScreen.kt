package features.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import base.compose.colorPrimary
import base.compose.white
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import features.ImageList.view.ImageDetailScreen
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


object SplashScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        SplashScreenContent(
            painterResource("sign_in_logo.png"),
            "0.0.1",
            true
        )
    }
}

@Composable
fun SplashScreenContent(
    logo: Painter,
    version: String,
    showProgress: Boolean
) {
    val navigator = LocalNavigator.currentOrThrow
    LaunchedEffect(true){
//        delay(1000)
        navigator.push(LoginScreen)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorPrimary)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(0f.dp).weight(1f),)
        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
        )

        if (showProgress) {
            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = white)
            }
        }
        Spacer(Modifier.height(0f.dp).weight(1f),)
        Text(
            text = version,
            color = white,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

