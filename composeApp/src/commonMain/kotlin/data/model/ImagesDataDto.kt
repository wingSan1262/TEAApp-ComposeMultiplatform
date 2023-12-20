package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ImagesDataDto(
    val id: String?,
    val slug: String?,
    @SerialName("created_at")
    val createdAt: String?,
    val width: Long?,
    val height: Long?,
    val color: String?,
    @SerialName("blur_hash")
    val blurHash: String?,
    val description: String?,
    @SerialName("alt_description")
    val altDescription: String?,
    val urls: Urls?,
    val likes: Long?,
    @SerialName("liked_by_user")
    val likedByUser: Boolean?,
    val user: User?,
)

@Serializable
data class Urls(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?,
    @SerialName("small_s3")
    val smallS3: String?,
)

@Serializable
data class User(
    val id: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    val username: String?,
    val name: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("twitter_username")
    val twitterUsername: String?,
    @SerialName("portfolio_url")
    val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    val links: Links?,
    @SerialName("profile_image")
    val profileImage: ProfileImage?,
)

@Serializable
data class Links(
    val self: String?,
    val html: String?,
    val photos: String?,
    val likes: String?,
    val portfolio: String?,
    val following: String?,
    val followers: String?,
)

@Serializable
data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String,
)