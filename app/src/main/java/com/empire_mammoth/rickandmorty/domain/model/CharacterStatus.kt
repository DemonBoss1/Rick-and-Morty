package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class CharacterStatus(val apiValue: String) {
    @SerialName("alive") ALIVE("alive"),
    @SerialName("dead") DEAD("dead"),
    @SerialName("unknown") UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): CharacterStatus? {
            return entries.find { it.apiValue.equals(value, ignoreCase = true) }
        }
    }
}