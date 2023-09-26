package com.jfgjunior.marvel_characters.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.map
import com.jfgjunior.marvel_characters.domain.usecase.FetchCharactersUseCase
import com.jfgjunior.marvel_characters.ui.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCharactersUseCase: FetchCharactersUseCase,
) : ViewModel() {
    private val stateFlow = MutableStateFlow<HomeState>(HomeState.Initializing)
    val state: StateFlow<HomeState>
        get() = stateFlow

    init {
        val result = fetchCharactersUseCase()
            .map {
                it.map {
                        data -> CharacterModel(data.id, data.name, data.thumbnail, data.description)
                }
            }
        stateFlow.value = HomeState.Characters(result)
    }
}