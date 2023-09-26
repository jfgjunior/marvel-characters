package com.jfgjunior.marvel_characters.data.repository

import com.jfgjunior.marvel_characters.data.dto.CharacterDTO
import com.jfgjunior.marvel_characters.data.dto.CharacterDataWrapperDTO
import com.jfgjunior.marvel_characters.data.dto.ComicDataWrapperDTO

interface Cache {
    fun putCharacters(page: Int, items: CharacterDataWrapperDTO)
    fun getCharacters(page: Int): CharacterDataWrapperDTO?
    fun getAllCharacters(): List<CharacterDTO>

    fun putComics(page: Int, characterId: Int, items: ComicDataWrapperDTO)
    fun getComics(page: Int, characterId: Int): ComicDataWrapperDTO?
}