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
    fun toQueryMap(): Map<String, String> {
        return buildMap {
            name?.takeIf { it.isNotBlank() }?.let { put("name", it) }
            status?.let { put("status", it.apiValue) }
            species?.takeIf { it.isNotBlank() }?.let { put("species", it) }
            type?.takeIf { it.isNotBlank() }?.let { put("type", it) }
            gender?.let { put("gender", it.apiValue) }
        }
    }

    val isEmpty: Boolean
        get() = name.isNullOrBlank() &&
                status == null &&
                species.isNullOrBlank() &&
                type.isNullOrBlank() &&
                gender == null
}