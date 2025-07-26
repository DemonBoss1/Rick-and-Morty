package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterFilter(
    val status: CharacterStatus? = null,
    val species: String? = null,
    val gender: CharacterGender? = null
) {
    fun toQueryMap(): Map<String, String> {
        return buildMap {
            status?.let { put("status", it.apiValue) }
            species?.takeIf { it.isNotBlank() }?.let { put("species", it) }
            gender?.let { put("gender", it.apiValue) }
        }
    }
}