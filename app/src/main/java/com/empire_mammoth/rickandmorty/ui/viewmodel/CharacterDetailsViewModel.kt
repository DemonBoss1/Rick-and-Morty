package com.empire_mammoth.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire_mammoth.rickandmorty.data.repository.CharacterRepository
import com.empire_mammoth.rickandmorty.ui.navigation.model.CharacterDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> = _state

    fun loadCharacter(characterId: Int) {
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val character = repository.getCharacter(characterId)
                val episodes = if (character.episode.isNotEmpty()) {
                    repository.getEpisodes(character.episode)
                } else {
                    emptyList()
                }

                _state.value = _state.value.copy(
                    character = character,
                    episodes = episodes,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to load character",
                    isLoading = false
                )
            }
        }
    }
}

