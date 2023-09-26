package com.jfgjunior.marvel_characters.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.jfgjunior.marvel_characters.data.dto.ComicDTO
import com.jfgjunior.marvel_characters.data.repository.MarvelRepository
import com.jfgjunior.marvel_characters.domain.mapper.ComicsDtoToDataMapper
import com.jfgjunior.marvel_characters.domain.model.ComicData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchCharacterComicsUseCase @Inject constructor(
    private val repository: MarvelRepository,
    private val comicsDtoToDataMapper: ComicsDtoToDataMapper,
) {
    operator fun invoke(id: Int): Flow<PagingData<ComicData>> {
        return repository.fetchCharactersComics(id)
            .map { it.map { dto -> comicsDtoToDataMapper.map(dto) } }
            .flowOn(Dispatchers.IO)
    }
}