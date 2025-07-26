package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens {
    @Serializable
    data class CharactersList(val filter: Map<String, String>) : AppScreens()

    @Serializable
    data class CharacterDetails(val id: Int) : AppScreens()

    @Serializable
    data class FilterScreen(val currentFilter: Map<String, String>) : AppScreens()
}