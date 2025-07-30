package com.empire_mammoth.rickandmorty.data.api

import com.empire_mammoth.rickandmorty.data.model.CharactersResponse
import com.empire_mammoth.rickandmorty.data.model.Character
import com.empire_mammoth.rickandmorty.data.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") ids: String): List<Character>

    @GET("episode/{ids}")
    suspend fun getMultipleEpisodes(@Path("ids") ids: String): List<Episode>
}