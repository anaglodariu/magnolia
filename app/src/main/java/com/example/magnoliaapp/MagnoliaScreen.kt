package com.example.magnoliaapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.magnoliaapp.ui.screens.MapScreen
import com.example.magnoliaapp.ui.screens.MagnoliaDetailsScreen
import com.example.magnoliaapp.ui.screens.SettingsScreen

enum class MagnoliaScreen {
    Map,
    Details,
    Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MagnoliaAppBar(
    currentScreen: MagnoliaScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateToSettings: () -> Unit
) {

    TopAppBar(

        title = {
            Text(text = currentScreen.name)
        },

        navigationIcon = {

            if (canNavigateBack) {

                IconButton(
                    onClick = navigateUp
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription = "Back"
                    )
                }
            }
        },

        actions = {

            if (currentScreen == MagnoliaScreen.Map) {

                IconButton(
                    onClick = navigateToSettings
                ) {

                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        }
    )
}

@Composable
fun MagnoliaApp(
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        backStackEntry?.destination?.route

    val currentScreen = when {

        currentRoute?.startsWith(
            MagnoliaScreen.Details.name
        ) == true -> MagnoliaScreen.Details

        currentRoute == MagnoliaScreen.Settings.name ->
            MagnoliaScreen.Settings

        else -> MagnoliaScreen.Map
    }

    Scaffold(

        topBar = {

            MagnoliaAppBar(

                currentScreen = currentScreen,

                canNavigateBack =
                    navController.previousBackStackEntry != null,

                navigateUp = {
                    navController.navigateUp()
                },

                navigateToSettings = {
                    navController.navigate(
                        MagnoliaScreen.Settings.name
                    )
                }
            )
        }

    ) { innerPadding ->

        NavHost(

            navController = navController,

            startDestination =
                MagnoliaScreen.Map.name,

            modifier = Modifier.padding(innerPadding)
        ) {

            composable(
                route = MagnoliaScreen.Map.name
            ) {

                MapScreen(

                    onMagnoliaClick = { magnolia ->

                        navController.navigate(
                            "${MagnoliaScreen.Details.name}/${magnolia.id}"
                        )
                    }
                )
            }

            composable(

                route = "${MagnoliaScreen.Details.name}/{magnoliaId}",

                arguments = listOf(

                    navArgument("magnoliaId") {
                        type = NavType.IntType
                    }
                )

            ) { backStackEntry ->

                val magnoliaId =
                    backStackEntry.arguments
                        ?.getInt("magnoliaId") ?: 0

                MagnoliaDetailsScreen(
                    magnoliaId = magnoliaId
                )
            }

            composable(
                route = MagnoliaScreen.Settings.name
            ) {

                SettingsScreen()
            }
        }
    }
}