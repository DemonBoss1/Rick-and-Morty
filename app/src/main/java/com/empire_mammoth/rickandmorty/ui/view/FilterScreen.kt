package com.empire_mammoth.rickandmorty.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.empire_mammoth.rickandmorty.domain.model.CharacterFilter
import com.empire_mammoth.rickandmorty.domain.model.CharacterGender
import com.empire_mammoth.rickandmorty.domain.model.CharacterStatus

@Composable
fun FilterScreen(
    currentFilter: CharacterFilter?,
    onApplyFilter: (CharacterFilter) -> Unit,
    onDismiss: () -> Unit
) {
    var status by remember { mutableStateOf(currentFilter?.status) }
    var species by remember { mutableStateOf(currentFilter?.species ?: "") }
    var gender by remember { mutableStateOf(currentFilter?.gender) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Status", style = MaterialTheme.typography.titleSmall)
        CharacterStatus.entries.forEach { item ->
            FilterChip(
                selected = status == item,
                onClick = { status = if (status == item) null else item },
                label = { Text(item.name) },
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = species,
            onValueChange = { species = it },
            label = { Text("Species") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text("Gender", style = MaterialTheme.typography.titleSmall)
        CharacterGender.entries.forEach { item ->
            FilterChip(
                selected = gender == item,
                onClick = { gender = if (gender == item) null else item },
                label = { Text(item.name) },
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    onApplyFilter(
                        CharacterFilter(
                            status = status,
                            species = species.takeIf { it.isNotBlank() },
                            gender = gender
                        )
                    )
                },
                enabled = status != null || species.isNotBlank() || gender != null
            ) {
                Text("Apply Filters")
            }
        }
    }
}