package com.jfgjunior.marvel_characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jfgjunior.marvel_characters.ui.character.CharacterPage
import com.jfgjunior.marvel_characters.ui.home.HomePage
import com.jfgjunior.marvel_characters.ui.theme.MarvelcharactersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelcharactersTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomePage { id, name ->
                            navController.navigate(
                                "details/$id/$name"
                            )
                        }
                    }
                    composable(
                        "details/{characterId}/{characterName}",
                        arguments = listOf(
                            navArgument("characterId") {
                                type = NavType.IntType
                            },
                            navArgument("characterName") {
                                type = NavType.StringType
                            },
                        )
                    ) {
                        CharacterPage(
                            it.arguments?.getString("characterName").orEmpty(),
                            navController::navigateUp
                        )
                    }
                }
            }
        }
    }
}