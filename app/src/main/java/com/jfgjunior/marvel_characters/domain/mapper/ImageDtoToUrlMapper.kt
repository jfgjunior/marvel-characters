package com.jfgjunior.marvel_characters.domain.mapper

import com.jfgjunior.marvel_characters.data.dto.ImageDTO
import javax.inject.Inject

class ImageDtoToUrlMapper @Inject constructor() {
    fun map(dto: ImageDTO?): String {
        if (dto?.path == null || dto.extension == null) {
            return ""
        }
        return "${dto.path.replace("http://", "https://")}.${dto.extension}"
    }
}