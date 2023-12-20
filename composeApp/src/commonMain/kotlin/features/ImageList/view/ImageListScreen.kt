package features.ImageList.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import base.extensions.LoadUrlImage
import base.extensions.collectResourceState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.model.ImagesDataDto
import features.ImageList.ViewModel.ImageListModel
import io.github.aakira.napier.Napier
import org.koin.compose.koinInject

object ImageListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ImageListScreenContent{
            navigator.push(ImageDetailScreen(it))
        }
    }
}

@Composable
fun ImageListScreenContent(
    onClick: (ImagesDataDto)->Unit
){
    val photoViewModel = koinInject<ImageListModel>()
    val (photoSuccess, photoError, _,data, ) = photoViewModel.photoListData.collectResourceState()

    if(!photoSuccess && !photoError)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator()
        }

    LazyColumn {
        itemsIndexed(
            items = data ?: listOf(),
            key = { index, item ->
                item.id ?: index
            },
        ){index, item ->
            Card(
                elevation = 8f.dp,
                modifier = Modifier.padding(8f.dp).clickable{onClick(item)}
            ) {
                Column{
                    Text(
                        text = item.user?.username ?: "Unknown",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8f.dp).fillMaxWidth()
                    )

                    Napier.d("test ${item.urls?.regular}")
                    LoadUrlImage(
                        item.urls?.regular ?: "",
                    )
                }
            }
        }
    }

    LaunchedEffect(true){
        photoViewModel.getPhoto()
    }
}


