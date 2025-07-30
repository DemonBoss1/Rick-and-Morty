package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens {
    @Serializable
    data object CharactersList : AppScreens()

    @Serializable
    data class CharacterDetails(val id: Int) : AppScreens()

    @Serializable
    data object FilterScreen : AppScreens()
}