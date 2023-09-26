package com.jfgjunior.marvel_characters.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jfgjunior.marvel_characters.core.MarvelApi
import com.jfgjunior.marvel_characters.data.dto.CharacterDTO
import com.jfgjunior.marvel_characters.data.dto.ComicDTO
import com.jfgjunior.marvel_characters.data.repository.datasource.CharactersDataSource
import com.jfgjunior.marvel_characters.data.repository.datasource.ComicsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val charactersDataSource: CharactersDataSource,
    private val api: MarvelApi,
    private val cache: Cache,
) : MarvelRepository {

    override fun fetchCharacters(): Flow<PagingData<CharacterDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { charactersDataSource }
        ).flow
    }

    override fun fetchCharactersComics(
        characterId: Int
    ): Flow<PagingData<ComicDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                ComicsDataSource(api, cache, characterId)
            }
        ).flow
    }

    override fun getLocalCharacters(): List<CharacterDTO> {
        return cache.getAllCharacters()
    }
}