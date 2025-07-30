package com.empire_mammoth.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.empire_mammoth.rickandmorty.domain.model.AppScreens
import com.empire_mammoth.rickandmorty.domain.model.CharacterFilter
import com.empire_mammoth.rickandmorty.ui.navigation.model.CharacterFilterNavType
import com.empire_mammoth.rickandmorty.ui.view.CharacterDetailsScreen
import com.empire_mammoth.rickandmorty.ui.view.CharactersScreen
import com.empire_mammoth.rickandmorty.ui.view.FilterScreen
import com.empire_mammoth.rickandmorty.ui.viewmodel.CharactersViewModel
import kotlin.reflect.typeOf

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()
    val charactersViewModel: CharactersViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.CharactersList
    ) {
        composable<AppScreens.CharactersList> {

            CharactersScreen(
                onCharacterSelected = { id ->
                    navController.navigate(AppScreens.CharacterDetails(id))
                },
                onFilterClick = {
                    navController.navigate(AppScreens.FilterScreen)
                },
                viewModel = charactersViewModel
            )
        }

        composable<AppScreens.CharacterDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppScreens.CharacterDetails>()
            CharacterDetailsScreen(
                characterId = args.id,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<AppScreens.FilterScreen> {

            FilterScreen(
                currentFilter = charactersViewModel.currentFilter.value,
                onApplyFilter = { newFilter ->
                    charactersViewModel.applyFilter(newFilter)
                    navController.navigate(
                        AppScreens.CharactersList
                    ) {
                        popUpTo<AppScreens.CharactersList>()
                    }
                },
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}