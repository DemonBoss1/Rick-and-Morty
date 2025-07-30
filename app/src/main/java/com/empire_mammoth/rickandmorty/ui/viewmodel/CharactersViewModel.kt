package com.empire_mammoth.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire_mammoth.rickandmorty.data.model.Character
import com.empire_mammoth.rickandmorty.data.repository.CharacterRepository
import com.empire_mammoth.rickandmorty.domain.model.CharacterFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _filter = MutableStateFlow<CharacterFilter?>(null)
    val currentFilter: StateFlow<CharacterFilter?> = _filter

    private var currentPage = 1
    private var totalPages = 1

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        if (_isLoading.value || currentPage > totalPages) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getCharacters(
                    page = currentPage,
                    filter = _filter.value
                )
                _characters.value += response.results
                totalPages = response.info.pages
                currentPage++
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun applyFilter(filter: CharacterFilter) {
        currentPage = 1
        _characters.value = emptyList()
        _filter.value = filter
        loadCharacters()
    }

    fun clearFilter() {
        _filter.value = null
        loadCharacters()
    }
}