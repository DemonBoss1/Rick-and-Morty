package com.empire_mammoth.rickandmorty.ui.view

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.empire_mammoth.rickandmorty.domain.model.CharacterFilter
import com.empire_mammoth.rickandmorty.domain.model.CharacterGender
import com.empire_mammoth.rickandmorty.domain.model.CharacterStatus

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    currentFilter: CharacterFilter? = null,
    onApplyFilter: (CharacterFilter) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(currentFilter?.name ?: "") }
    var status by remember { mutableStateOf(currentFilter?.status) }
    var species by remember { mutableStateOf(currentFilter?.species ?: "") }
    var type by remember { mutableStateOf(currentFilter?.type ?: "") }
    var gender by remember { mutableStateOf(currentFilter?.gender) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Status",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            CharacterStatus.entries.forEach { item ->
                FilterChip(
                    selected = status == item,
                    onClick = { status = if (status == item) null else item },
                    label = { Text(item.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        OutlinedTextField(
            value = species,
            onValueChange = { species = it },
            label = { Text("Species") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.medium
        )

        Text(
            text = "Gender",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            CharacterGender.entries.forEach { item ->
                FilterChip(
                    selected = gender == item,
                    onClick = { gender = if (gender == item) null else item },
                    label = { Text(item.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    status = null
                    species = ""
                    gender = null
                }
            ) {
                Text("Reset")
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        onApplyFilter(
                            CharacterFilter(
                                name = name.takeIf { it.isNotBlank() },
                                status = status,
                                species = species.takeIf { it.isNotBlank() },
                                type = type.takeIf { it.isNotBlank() },
                                gender = gender
                            )
                        )
                    },
                    enabled = name.isNotBlank() || status != null ||
                            species.isNotBlank() || type.isNotBlank() || gender != null
                ) {
                    Text("Apply Filters")
                }
            }
        }
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    MaterialTheme {
        FilterScreen(
            onApplyFilter = {},
            onDismiss = {}
        )
    }
}