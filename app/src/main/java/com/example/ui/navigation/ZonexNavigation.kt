package com.example.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.ActiveRunScreen
import com.example.ui.screens.AvatarScreen
import com.example.ui.screens.ConquestScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MainMapScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.WelcomeScreen

object ZonexDestinations {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MAIN_MAP = "main_map"
    const val PROFILE = "profile"
    const val AVATAR = "avatar"
    const val ACTIVE_RUN = "active_run"
    const val CONQUEST = "conquest"
}

@Composable
fun ZonexNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ZonexDestinations.WELCOME
) {
    var highlightConquestOnMap by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        composable(ZonexDestinations.WELCOME) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(ZonexDestinations.LOGIN)
                },
                onNavigateToRegister = {
                    navController.navigate(ZonexDestinations.REGISTER)
                }
            )
        }

        composable(ZonexDestinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(ZonexDestinations.MAIN_MAP) {
                        popUpTo(ZonexDestinations.WELCOME) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(ZonexDestinations.REGISTER)
                }
            )
        }

        composable(ZonexDestinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(ZonexDestinations.MAIN_MAP) {
                        popUpTo(ZonexDestinations.WELCOME) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(ZonexDestinations.LOGIN) {
                        popUpTo(ZonexDestinations.REGISTER) { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(ZonexDestinations.MAIN_MAP) {
            MainMapScreen(
                onNavigateToRun = {
                    navController.navigate(ZonexDestinations.ACTIVE_RUN)
                },
                onNavigateToProfile = {
                    navController.navigate(ZonexDestinations.PROFILE)
                },
                onNavigateToAvatar = {
                    navController.navigate(ZonexDestinations.AVATAR)
                },
                highlightConqueredTerritory = highlightConquestOnMap
            )
        }

        composable(ZonexDestinations.PROFILE) {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToAvatar = {
                    navController.navigate(ZonexDestinations.AVATAR)
                },
                onNavigateToMap = {
                    navController.navigate(ZonexDestinations.MAIN_MAP) {
                        popUpTo(ZonexDestinations.MAIN_MAP) { inclusive = true }
                    }
                }
            )
        }

        composable(ZonexDestinations.AVATAR) {
            AvatarScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(ZonexDestinations.ACTIVE_RUN) {
            ActiveRunScreen(
                onConcludeRun = {
                    navController.navigate(ZonexDestinations.CONQUEST) {
                        popUpTo(ZonexDestinations.ACTIVE_RUN) { inclusive = true }
                    }
                },
                onExitRun = {
                    navController.popBackStack()
                }
            )
        }

        composable(ZonexDestinations.CONQUEST) {
            ConquestScreen(
                onContinue = {
                    highlightConquestOnMap = false
                    navController.navigate(ZonexDestinations.MAIN_MAP) {
                        popUpTo(ZonexDestinations.MAIN_MAP) { inclusive = true }
                    }
                },
                onViewTerritory = {
                    highlightConquestOnMap = true
                    navController.navigate(ZonexDestinations.MAIN_MAP) {
                        popUpTo(ZonexDestinations.MAIN_MAP) { inclusive = true }
                    }
                }
            )
        }
    }
}
