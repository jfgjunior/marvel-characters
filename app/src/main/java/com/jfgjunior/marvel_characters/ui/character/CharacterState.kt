package com.jfgjunior.marvel_characters.ui.character

import androidx.paging.PagingData
import com.jfgjunior.marvel_characters.ui.model.CharacterModel
import com.jfgjunior.marvel_characters.ui.model.ComicModel
import kotlinx.coroutines.flow.Flow


sealed class CharacterState {
    object Loading : CharacterState()
    data class Content(
        val character: CharacterModel,
        val comicsFlow: Flow<PagingData<ComicModel>>,
    ) : CharacterState()
}