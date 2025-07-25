package com.empire_mammoth.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire_mammoth.rickandmorty.data.model.Info
import com.empire_mammoth.rickandmorty.data.model.Character
import com.empire_mammoth.rickandmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val repository = CharacterRepository()

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _paginationInfo = MutableStateFlow<Info?>(null)
    val paginationInfo: StateFlow<Info?> = _paginationInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCharacters(page: Int = 1) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.loadCharactersPage(page)
                _characters.value = response.results
                _paginationInfo.value = response.info
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.filterCharacters(
                    name = name,
                    status = status,
                    species = species,
                    type = type,
                    gender = gender,
                    page = page
                )
                _characters.value = response.results
                _paginationInfo.value = response.info
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}