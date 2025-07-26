package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class CharacterGender(val apiValue: String) {
    @SerialName("female") FEMALE("female"),
    @SerialName("male") MALE("male"),
    @SerialName("genderless") GENDERLESS("genderless"),
    @SerialName("unknown") UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): CharacterGender? {
            return entries.find { it.apiValue.equals(value, ignoreCase = true) }
        }
    }
}