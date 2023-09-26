package com.jfgjunior.marvel_characters.ui.home

import androidx.paging.PagingData
import com.jfgjunior.marvel_characters.ui.model.CharacterModel
import kotlinx.coroutines.flow.Flow

sealed class HomeState {
    object Initializing : HomeState()
    data class Characters(
        val characters: Flow<PagingData<CharacterModel>>,
    ) : HomeState()
}