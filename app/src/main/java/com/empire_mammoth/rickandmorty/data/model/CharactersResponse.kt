package com.empire_mammoth.rickandmorty.data.model

data class CharactersResponse(
    val info: Info,
    val results: List<Character>
)