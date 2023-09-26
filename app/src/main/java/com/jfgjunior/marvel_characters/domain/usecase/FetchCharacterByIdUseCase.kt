package com.jfgjunior.marvel_characters.domain.usecase

import com.jfgjunior.marvel_characters.data.repository.MarvelRepository
import com.jfgjunior.marvel_characters.domain.mapper.CharacterDtoToDataMapper
import com.jfgjunior.marvel_characters.domain.model.CharacterData
import javax.inject.Inject

class FetchCharacterByIdUseCase @Inject constructor(
    private val repository: MarvelRepository,
    private val characterDtoToDataMapper: CharacterDtoToDataMapper,
) {
    operator fun invoke(id: Int): CharacterData {
        return repository.getLocalCharacters()
            .first { it.id == id }
            .run {
                characterDtoToDataMapper.map(this)
            }
    }
}