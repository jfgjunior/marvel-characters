package com.jfgjunior.marvel_characters.data.dto

data class ComicDataContainerDTO(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val results: List<ComicDTO>?
)