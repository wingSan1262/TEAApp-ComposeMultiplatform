package data.repo

import io.ktor.client.*
import base.infra.doGetWithField
import data.model.ImagesDataDto
import data.repo.RepoConstants.Companion.UNSPLAH_PHOTO_URI
import kotlinx.coroutines.delay

class ImageApiImpl(
    val httpClient: HttpClient
) : ImageApi {
    companion object{
        const val client_id_field = "client_id"
    }
    override suspend fun getImage(accessKey: String): List<ImagesDataDto> {
        return httpClient
            .doGetWithField<List<ImagesDataDto>>(UNSPLAH_PHOTO_URI){
            it.append(client_id_field, accessKey)
        }
    }

}

interface ImageApi {
    suspend fun getImage(accessKey : String) : List<ImagesDataDto>
}