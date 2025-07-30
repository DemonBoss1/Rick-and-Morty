package com.empire_mammoth.rickandmorty.ui.navigation.model

import com.empire_mammoth.rickandmorty.data.model.Character
import com.empire_mammoth.rickandmorty.data.model.Episode

data class CharacterDetailsState(
    val character: Character? = null,
    val episodes: List<Episode> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)