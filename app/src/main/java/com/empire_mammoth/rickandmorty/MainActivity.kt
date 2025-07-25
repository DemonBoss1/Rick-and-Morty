package com.empire_mammoth.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.empire_mammoth.rickandmorty.ui.theme.RickAndMortyTheme
import com.empire_mammoth.rickandmorty.ui.view.CharactersScreen
import com.empire_mammoth.rickandmorty.ui.viewmodel.CharactersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharactersScreen()
                }
            }
        }
    }
}