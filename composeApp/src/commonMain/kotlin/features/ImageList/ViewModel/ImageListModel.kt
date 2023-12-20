package features.ImageList.ViewModel

import cafe.adriel.voyager.core.model.ScreenModel
import domain.usecase.PhotoUseCase

class ImageListModel(
    private val useCase: PhotoUseCase
) : ScreenModel {
    val photoListData = useCase.currentData
    fun getPhoto() { useCase.setup(null) }
}