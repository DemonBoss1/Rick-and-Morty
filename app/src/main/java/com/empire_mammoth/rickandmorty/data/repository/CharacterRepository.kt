package com.empire_mammoth.rickandmorty.data.repository

import com.empire_mammoth.rickandmorty.data.api.RickAndMortyApiService
import com.empire_mammoth.rickandmorty.data.model.Character
import com.empire_mammoth.rickandmorty.data.model.CharactersResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApiService
)  {
    suspend fun loadAllCharacters(): List<Character> {
        val allCharacters = mutableListOf<Character>()
        var currentPage = 1
        var totalPages = 1

        while (currentPage <= totalPages) {
            val response = api.getCharacters(page = currentPage)
            allCharacters.addAll(response.results)
            totalPages = response.info.pages
            currentPage++

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

    suspend fun getCharacter(characterId: Int) = api.getCharacter(characterId)
}