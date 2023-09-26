package com.jfgjunior.marvel_characters.data.repository

import androidx.paging.PagingData
import com.jfgjunior.marvel_characters.data.dto.CharacterDTO
import com.jfgjunior.marvel_characters.data.dto.ComicDTO
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {
    fun fetchCharacters(): Flow<PagingData<CharacterDTO>>
    fun fetchCharactersComics(characterId: Int): Flow<PagingData<ComicDTO>>
    fun getLocalCharacters(): List<CharacterDTO>
}