package com.empire_mammoth.rickandmorty.ui.navigation.model

import com.empire_mammoth.rickandmorty.data.model.Character

data class CharacterDetailsState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)