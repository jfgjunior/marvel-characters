package com.jfgjunior.marvel_characters.domain.mapper

import com.jfgjunior.marvel_characters.data.dto.CharacterDTO
import com.jfgjunior.marvel_characters.domain.model.CharacterData
import javax.inject.Inject

class CharacterDtoToDataMapper @Inject constructor(
    val imageMapper: ImageDtoToUrlMapper,
) {

    fun map(dto: CharacterDTO): CharacterData = CharacterData(
        id = dto.id ?: -1,
        name = dto.name.orEmpty(),
        description = dto.description.orEmpty(),
        thumbnail = imageMapper.map(dto.thumbnail)
    )
}