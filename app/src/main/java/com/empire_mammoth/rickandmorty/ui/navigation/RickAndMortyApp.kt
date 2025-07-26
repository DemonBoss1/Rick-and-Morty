package com.empire_mammoth.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.empire_mammoth.rickandmorty.domain.model.AppScreens
import com.empire_mammoth.rickandmorty.ui.view.CharacterDetailsScreen
import com.empire_mammoth.rickandmorty.ui.view.CharactersScreen

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.CharactersList
    ) {
        // Список персонажей
        composable<AppScreens.CharactersList> {
            CharactersScreen(
                onCharacterSelected = { id ->
                    navController.navigate(AppScreens.CharacterDetails(id))
                },
                onFilterClick = {
//                                status, species, gender ->
//                    navController.navigate(AppScreens.FilterOptions(status, species, gender))
                }
            )
        }

        // Детали персонажа
        composable<AppScreens.CharacterDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppScreens.CharacterDetails>()
            CharacterDetailsScreen(
                characterId = args.id,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

//        // Фильтры
//        composable<AppScreens.FilterOptions> { backStackEntry ->
//            val args = backStackEntry.toRoute<AppScreens.FilterOptions>()
//            FilterScreen(
//                initialStatus = args.status,
//                initialSpecies = args.species,
//                initialGender = args.gender,
//                onApply = { status, species, gender ->
//                    navController.navigate(AppScreens.FilterOptions(status, species, gender)) {
//                        popUpTo<AppScreens.CharactersList>()
//                    }
//                }
//            )
//        }
    }
}