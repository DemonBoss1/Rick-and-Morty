package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterFilter(
    val name: String? = null,
    val status: CharacterStatus? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: CharacterGender? = null
) {
    fun isNotEmpty(): Boolean {
        return !name.isNullOrBlank() ||
                status != null ||
                !species.isNullOrBlank() ||
                !type.isNullOrBlank() ||
                gender != null
    }
}