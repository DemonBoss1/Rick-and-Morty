package com.empire_mammoth.rickandmorty.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.empire_mammoth.rickandmorty.data.model.Character
import com.empire_mammoth.rickandmorty.ui.viewmodel.CharactersViewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onCharacterSelected: (Int) -> Unit,
    onFilterClick: () -> Unit,
) {
    val characters by remember { viewModel.characters }.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val filterState by viewModel.filterState.collectAsState()

    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo }
            .collect {
                if (it.lastOrNull()?.index == characters.size - 1 && !isLoading) {
                    viewModel.loadCharacters()
                }
            }
    }
    Scaffold(
        topBar = {
            RickAndMortyTopBar(
                title = "Characters",
                showFilter = true,
                onFilterClick = onFilterClick,
                filterActive = filterState != null
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (characters.isEmpty() && isLoading) {
                FullScreenLoading()
            } else if (error != null) {
                ErrorState(error = error, onRetry = { viewModel.loadCharacters() })
            } else {
                CharacterGrid(
                    characters = characters,
                    gridState = lazyGridState,
                    isLoading = isLoading,
                    onCharacterClick = onCharacterSelected
                )
            }
        }
    }
}

@Composable
private fun CharacterGrid(
    characters: List<Character>,
    gridState: LazyGridState,
    isLoading: Boolean,
    onCharacterClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(characters, key = { it.id }) { character ->
            CharacterCard(
                character = character,
                modifier = Modifier.padding(4.dp),
                onClick = { onCharacterClick(character.id) }
            )
        }

        if (isLoading) {
            item(span = { GridItemSpan(2) }) {
                LoadingItem()
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            CharacterDetails(character)
        }
    }
}

@Composable
fun CharacterDetails(character: Character) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = when (character.status) {
                            "Alive" -> Color.Green
                            "Dead" -> Color.Red
                            else -> Color.Gray
                        },
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = character.status,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }

        Text(
            text = "${character.species} • ${character.gender}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}