package com.empire_mammoth.rickandmorty.domain.model

import kotlinx.serialization.Serializable


@Serializable
enum class CharacterGender(val apiValue: String) {
    FEMALE("female"),
    MALE("male"),
    GENDERLESS("genderless"),
    UNKNOWN("unknown");

    companion object {
        fun fromApiValue(value: String): CharacterGender? {
            return entries.find { it.apiValue.equals(value, ignoreCase = true) }
        }
    }
}