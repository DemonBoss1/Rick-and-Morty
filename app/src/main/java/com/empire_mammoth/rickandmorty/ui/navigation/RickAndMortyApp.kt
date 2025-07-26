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
    val viewModel: CharactersViewModel = hiltViewModel()

    NavHost(
        navController = navController, startDestination = AppScreens.CharactersList(emptyMap())
    ) {
        composable<AppScreens.CharactersList> { backStackEntry ->
            val filter = backStackEntry.toRoute<AppScreens.CharactersList>().filter
            CharactersScreen(onCharacterSelected = { id ->
                navController.navigate(AppScreens.CharacterDetails(id))
            }, onFilterClick = {
                viewModel.currentFilter.value?.let {
                    navController.navigate(AppScreens.FilterScreen(viewModel.currentFilter.value!!.toQueryMap()))
                }
            })
        }

        composable<AppScreens.CharacterDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppScreens.CharacterDetails>()
            CharacterDetailsScreen(
                characterId = args.id, onBackClick = {
                    navController.popBackStack()
                })
        }

//        composable<AppScreens.FilterScreen> { backStackEntry ->
//            val args = backStackEntry.toRoute<AppScreens.FilterScreen>()
//            FilterScreen(currentFilter = args.currentFilter, onApplyFilter = { newFilter ->
//                navController.navigate(
//                    AppScreens.CharactersList(newFilter.toQueryMap())
//                ) {
//                    popUpTo<AppScreens.CharactersList>()
//                }
//            }, onDismiss = {
//                navController.popBackStack()
//            })
//        }
    }
}