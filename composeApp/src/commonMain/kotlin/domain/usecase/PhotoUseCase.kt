package domain.usecase

import base.base_classes.BaseUseCase
import data.model.ImagesDataDto
import data.repo.ImageApi

class PhotoUseCase(
    val api : ImageApi
) : BaseUseCase<Any?, List<ImagesDataDto>>() {

    override fun setup(parameter: Any?) {
        super.setup(parameter)
        execute {
            api.getImage("Cu76VtYFftDgrj0trpq4PC0KwJZX2ty67IBq6SHSofU")
        }
    }
}