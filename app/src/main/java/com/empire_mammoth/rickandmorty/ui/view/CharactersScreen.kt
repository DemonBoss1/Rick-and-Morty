package com.empire_mammoth.rickandmorty.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.empire_mammoth.rickandmorty.ui.viewmodel.CharactersViewModel
import com.empire_mammoth.rickandmorty.data.model.Character

@Composable
fun CharactersScreen(viewModel: CharactersViewModel = viewModel()) {
    val characters by viewModel.characters.collectAsState()
    val paginationInfo by viewModel.paginationInfo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (isLoading) {
        CircularProgressIndicator(Modifier.fillMaxSize())
    } else if (error != null) {
        Text("Error: $error")
    } else {
        LazyColumn {
            items(characters) { character ->
                CharacterItem(character = character)
            }

            paginationInfo?.next?.let {
                item {
                    Button(
                        onClick = {
                            val nextPage = it.substringAfter("page=").toIntOrNull()
                            nextPage?.let { page -> viewModel.loadCharacters(page) }
                        }
                    ) {
                        Text("Load Next Page")
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
    Text(text = character.name)
}