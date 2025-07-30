package com.empire_mammoth.rickandmorty.ui.navigation.model

import android.os.Bundle
import androidx.navigation.NavType
import com.empire_mammoth.rickandmorty.domain.model.CharacterFilter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CharacterFilterNavType : NavType<CharacterFilter?>(isNullableAllowed = true) {
    private val json = Json {
        ignoreUnknownKeys = true // Игнорировать неизвестные поля
        explicitNulls = false    // Позволяет пропускать null-значения
        coerceInputValues = true // Автоматически преобразовывать неверные enum-значения
    }

    override fun get(bundle: Bundle, key: String): CharacterFilter? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): CharacterFilter? {
        val data: CharacterFilter? = json.decodeFromString(value)
        return data
    }

    override fun put(bundle: Bundle, key: String, value: CharacterFilter?) {
        bundle.putString(key, value?.let { Json.encodeToString(it) })
    }
}