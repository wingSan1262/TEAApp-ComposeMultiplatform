package features.ImageList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.extensions.LoadUrlImage
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.model.ImagesDataDto

data class ImageDetailScreen(
    val data: ImagesDataDto
) : Screen {
    @Composable
    override fun Content() {
        ImageDetailScreenContent(data)
    }
}

@Composable
fun ImageDetailScreenContent(data: ImagesDataDto){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val navigator = LocalNavigator.currentOrThrow
        // Image
        // Back Button
        IconButton(
            onClick = { navigator.pop() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Black // Customize the color as needed
            )
        }

        LoadUrlImage(
            url = data.urls?.regular ?: "",
            modifier = Modifier
                .fillMaxWidth().height(200f.dp)
                .padding(8.dp)
                .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
                .background(Color.White, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Owner Profile
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoadUrlImage(
                    url = data.user?.profileImage?.small ?: "",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = data.user?.name ?: "", fontSize = 11.sp)
            }

            // Description
            Text(
                text = data.description ?: "",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Created At
            Text(
                text = "Created at: ${data.createdAt}",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}


