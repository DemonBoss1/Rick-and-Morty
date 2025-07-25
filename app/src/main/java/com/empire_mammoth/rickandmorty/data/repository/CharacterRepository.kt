package com.empire_mammoth.rickandmorty.data.repository

import com.empire_mammoth.rickandmorty.data.api.RickAndMortyApi
import com.empire_mammoth.rickandmorty.data.model.CharactersResponse
import com.empire_mammoth.rickandmorty.data.model.Character
import kotlinx.coroutines.delay

class CharacterRepository {
    private val api = RickAndMortyApi.service

    suspend fun loadAllCharacters(): List<Character> {
        val allCharacters = mutableListOf<Character>()
        var currentPage = 1
        var totalPages = 1

        while (currentPage <= totalPages) {
            val response = api.getCharacters(page = currentPage)
            allCharacters.addAll(response.results)
            totalPages = response.info.pages
            currentPage++

            // Можно добавить небольшую задержку между запросами
            delay(100)
        }

        return allCharacters
    }

    suspend fun loadCharactersPage(page: Int = 1): CharactersResponse {
        return api.getCharacters(page = page)
    }

    suspend fun filterCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null,
        page: Int? = null
    ): CharactersResponse {
        return api.getCharacters(
            page = page,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        )
    }
}