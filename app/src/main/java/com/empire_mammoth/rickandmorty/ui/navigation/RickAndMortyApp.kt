package com.empire_mammoth.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.empire_mammoth.rickandmorty.domain.model.AppScreens
import com.empire_mammoth.rickandmorty.domain.model.CharacterFilter
import com.empire_mammoth.rickandmorty.ui.view.CharacterDetailsScreen
import com.empire_mammoth.rickandmorty.ui.view.CharactersScreen
import com.empire_mammoth.rickandmorty.ui.view.FilterScreen
import com.empire_mammoth.rickandmorty.ui.viewmodel.CharactersViewModel

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.CharactersList
    ) {
        // Characters List Screen
        composable<AppScreens.CharactersList> {
            CharactersScreen(
                onCharacterSelected = { id ->
                    navController.navigate(AppScreens.CharacterDetails(id))
                },
                onFilterClick = {
                    navController.navigate(AppScreens.FilterScreen)
                }
            )
        }

        // Character Details Screen
        composable<AppScreens.CharacterDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppScreens.CharacterDetails>()
            CharacterDetailsScreen(
                characterId = args.id,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Filter Screen
        composable<AppScreens.FilterScreen> {
            FilterScreen(
                onApplyFilter = { filter ->
                    navController.navigate(
                        AppScreens.FilterOptions(
                            status = filter.status?.name,
                            species = filter.species,
                            gender = filter.gender?.name
                        )
                    ) {
                        popUpTo<AppScreens.CharactersList>()
                    }
                },
                onDismiss = { navController.popBackStack() }
            )
        }

        // Filter Options Handling
        composable<AppScreens.FilterOptions> { backStackEntry ->
            val args = backStackEntry.toRoute<AppScreens.FilterOptions>()
            // Apply filters and show characters list
            CharactersScreen(
                onCharacterSelected = { id ->
                    navController.navigate(AppScreens.CharacterDetails(id))
                },
                onFilterClick = {
                    navController.navigate(AppScreens.FilterScreen)
                }
            )
        }
    }
}