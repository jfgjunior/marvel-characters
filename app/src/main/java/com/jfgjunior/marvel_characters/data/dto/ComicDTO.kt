package com.jfgjunior.marvel_characters.data.dto

data class ComicDTO(
    val id: Int?,
    val title: String?,
    val thumbnail: ImageDTO?,
    val description: String?,
    val prices: List<ComicPriceDTO>?
)