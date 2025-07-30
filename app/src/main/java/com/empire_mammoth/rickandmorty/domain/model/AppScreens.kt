package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens {
    @Serializable
    object CharactersList : AppScreens()

    @Serializable
    data class CharacterDetails(val id: Int) : AppScreens()

    @Serializable
    object FilterScreen : AppScreens()

    @Serializable
    data class FilterOptions(
        val status: String? = null,
        val species: String? = null,
        val gender: String? = null
    ) : AppScreens()
}