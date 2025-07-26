package com.empire_mammoth.rickandmorty.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.empire_mammoth.rickandmorty.ui.viewmodel.CharacterDetailsViewModel
import com.empire_mammoth.rickandmorty.data.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    characterId: Int?,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onEpisodeClick: (Int) -> Unit = {},
    onLocationClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    LaunchedEffect(characterId) {
        characterId?.let { viewModel.loadCharacter(it) }
    }

    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            RickAndMortyTopBar(
                title = state.character?.name ?: "Loading...",
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        when {
            state.isLoading -> FullScreenLoading()
            state.error != null -> ErrorState(
                error = state.error,
                onRetry = { characterId?.let { viewModel.loadCharacter(it) } }
            )
            state.character != null -> CharacterDetailsContent(
                character = state.character!!,
                modifier = Modifier.padding(paddingValues),
                onEpisodeClick = onEpisodeClick,
                onLocationClick = onLocationClick
            )
        }
    }
}

@Composable
private fun CharacterDetailsContent(
    character: Character,
    modifier: Modifier = Modifier,
    onEpisodeClick: (Int) -> Unit,
    onLocationClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Аватар и базовая информация
        CharacterHeader(character)

        // Информационные карточки
        CharacterInfoCards(
            character = character,
            onLocationClick = onLocationClick
        )
    }
}

@Composable
private fun CharacterHeader(character: Character) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            StatusIndicator(character.status)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${character.species} • ${character.gender}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun StatusIndicator(status: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = when (status) {
                        "Alive" -> Color.Green
                        "Dead" -> Color.Red
                        else -> Color.Gray
                    },
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = status,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun CharacterInfoCards(
    character: Character,
    onLocationClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        character.origin.let { origin ->
            InfoCard(
                title = "Origin",
                content = origin.name,
                onClick = { origin.url.extractId()?.let(onLocationClick) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        character.location.let { location ->
            InfoCard(
                title = "Last Known Location",
                content = location.name,
                onClick = { location.url.extractId()?.let(onLocationClick) }
            )
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    content: String,
    onClick: (() -> Unit)? = null
) {
    Card(
        onClick = onClick ?: {},
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun String?.extractId(): Int? {
    return this?.split("/")?.lastOrNull()?.toIntOrNull()
}