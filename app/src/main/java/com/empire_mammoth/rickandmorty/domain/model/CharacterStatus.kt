package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.Serializable


@Serializable
enum class CharacterStatus(val apiValue: String) {
    ALIVE("alive"),
    DEAD("dead"),
    UNKNOWN("unknown");

    companion object {
        fun fromApiValue(value: String): CharacterStatus? {
            return entries.find { it.apiValue.equals(value, ignoreCase = true) }
        }
    }
}