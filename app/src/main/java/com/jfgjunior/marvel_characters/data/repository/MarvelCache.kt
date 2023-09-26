package com.jfgjunior.marvel_characters.data.repository

import com.jfgjunior.marvel_characters.data.dto.CharacterDTO
import com.jfgjunior.marvel_characters.data.dto.CharacterDataWrapperDTO
import com.jfgjunior.marvel_characters.data.dto.ComicDataWrapperDTO
import javax.inject.Inject

class MarvelCache @Inject constructor() : Cache {
    private val characters = mutableMapOf<Int, CharacterDataWrapperDTO>()
    private val comics = mutableMapOf<Int, MutableMap<Int, ComicDataWrapperDTO>>()

    override fun putCharacters(page: Int, items: CharacterDataWrapperDTO) {
        characters[page] = items
    }

    override fun getCharacters(page: Int): CharacterDataWrapperDTO? {
        if (characters.containsKey(page)) {
            return characters.getValue(page)
        }
        return null
    }

    override fun getAllCharacters(): List<CharacterDTO> {
        return characters.values.map { it.data?.results ?: listOf() }.flatten()
    }

    override fun putComics(page: Int, characterId: Int, items: ComicDataWrapperDTO) {
        if (!comics.containsKey(characterId)) {
            comics[characterId] = mutableMapOf()
        }
        comics[characterId]?.set(page, items)
    }

    override fun getComics(page: Int, characterId: Int): ComicDataWrapperDTO? {
        if (comics.containsKey(characterId)) {
            if (comics[characterId]?.containsKey(page) == true)
                return comics[characterId]?.getValue(page)
        }
        return null
    }
}