package com.jfgjunior.marvel_characters.domain.mapper

import com.jfgjunior.marvel_characters.data.dto.ComicDTO
import com.jfgjunior.marvel_characters.domain.model.ComicData
import javax.inject.Inject

class ComicsDtoToDataMapper @Inject constructor(
    private val imageDtoToUrlMapper: ImageDtoToUrlMapper,
) {
    fun map(dto: ComicDTO) = ComicData(
        id = dto.id ?: -1,
        description = dto.description.orEmpty(),
        imageUrl = imageDtoToUrlMapper.map(dto.thumbnail)
    )
}