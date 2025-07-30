package com.empire_mammoth.rickandmorty.data.repository

import com.empire_mammoth.rickandmorty.data.api.RickAndMortyApiService
import com.empire_mammoth.rickandmorty.data.model.Character
import com.empire_mammoth.rickandmorty.data.model.CharactersResponse
import com.empire_mammoth.rickandmorty.domain.model.CharacterFilter
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApiService
)  {
    suspend fun getCharacters(
        page: Int = 1,
        filter: CharacterFilter? = null
    ): CharactersResponse {
        return api.getCharacters(
            page = page,
            name = filter?.name,
            status = filter?.status?.apiValue,
            species = filter?.species,
            type = filter?.type,
            gender = filter?.gender?.apiValue
        )
    }

    suspend fun getCharacter(characterId: Int) = api.getCharacter(characterId)
}