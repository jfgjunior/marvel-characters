package com.jfgjunior.marvel_characters.ui.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.jfgjunior.marvel_characters.domain.usecase.FetchCharacterByIdUseCase
import com.jfgjunior.marvel_characters.domain.usecase.FetchCharacterComicsUseCase
import com.jfgjunior.marvel_characters.ui.model.CharacterModel
import com.jfgjunior.marvel_characters.ui.model.ComicModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchCharacterByIdUseCase: FetchCharacterByIdUseCase,
    fetchCharacterComicsUseCase: FetchCharacterComicsUseCase,
) : ViewModel() {
    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])
    private val mutableState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val state: StateFlow<CharacterState>
        get() = mutableState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val character = fetchCharacterByIdUseCase(characterId)
                mutableState.value = CharacterState.Content(
                    character = CharacterModel(
                        id = character.id,
                        name = character.name,
                        imageUrl = character.thumbnail,
                        description = character.description,
                    ),
                    comicsFlow = fetchCharacterComicsUseCase(characterId).map {
                        it.map { data ->
                            ComicModel(
                                id = data.id,
                                description = data.description,
                                imageUrl = data.imageUrl
                            )
                        }
                    },
                )
            }
        }
    }
}