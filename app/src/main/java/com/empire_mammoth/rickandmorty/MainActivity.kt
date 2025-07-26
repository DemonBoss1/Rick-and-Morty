package com.empire_mammoth.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.empire_mammoth.rickandmorty.ui.navigation.RickAndMortyApp
import com.empire_mammoth.rickandmorty.ui.theme.RickAndMortyTheme
import com.empire_mammoth.rickandmorty.ui.view.CharactersScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                RickAndMortyApp()
            }
        }
    }
}