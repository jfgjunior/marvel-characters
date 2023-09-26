package com.jfgjunior.marvel_characters.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.jfgjunior.marvel_characters.data.repository.MarvelRepository
import com.jfgjunior.marvel_characters.domain.mapper.CharacterDtoToDataMapper
import com.jfgjunior.marvel_characters.domain.model.CharacterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchCharactersUseCase @Inject constructor(
    private val repository: MarvelRepository,
    private val characterDtoToDataMapper: CharacterDtoToDataMapper,
) {
    operator fun invoke(): Flow<PagingData<CharacterData>> =
        repository.fetchCharacters()
            .flowOn(Dispatchers.IO)
            .map { it.map { dto -> characterDtoToDataMapper.map(dto) } }
}