package com.andreykosarygin.luckyfuhottei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreykosarygin.common.Routes
import com.andreykosarygin.game_ui.screen_game.ScreenGame
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel
import com.andreykosarygin.game_ui.screen_ho_to_play.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_ho_to_play.ScreenHowToPlayViewModel
import com.andreykosarygin.game_ui.screen_levels.ScreenLevels
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel
import com.andreykosarygin.game_ui.screen_lose.ScreenLose
import com.andreykosarygin.game_ui.screen_lose.ScreenLoseViewModel
import com.andreykosarygin.game_ui.screen_win.ScreenWin
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel
import com.andreykosarygin.main_ui.screen_menu.ScreenMenu
import com.andreykosarygin.main_ui.screen_menu.ScreenMenuViewModel
import com.andreykosarygin.main_ui.screen_splash.ScreenSplash
import com.andreykosarygin.main_ui.screen_splash.ScreenSplashViewModel
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasury
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasuryViewModel
import com.andreykosarygin.main_ui.screen_welcome_bonus.ScreenWelcomeBonus
import com.andreykosarygin.main_ui.screen_welcome_bonus.ScreenWelcomeBonusViewModel


class MainActivity : ComponentActivity() {
    private fun getApplicationInstance() = application as MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Routes.SCREEN_SPLASH) {
                composable(route = Routes.SCREEN_SPLASH) {
                    ScreenSplash(
                        navController = navController,
                        viewModel = ScreenSplashViewModel(
                            interactor = getApplicationInstance().interactorImplMainDomain
                        )
                    )
                }

                composable(route = Routes.SCREEN_WELCOME_BONUS) {
                    ScreenWelcomeBonus(
                        navController = navController,
                        viewModel = ScreenWelcomeBonusViewModel(
                            interactor = getApplicationInstance().interactorImplMainDomain
                        )
                    )
                }

                composable(route = Routes.SCREEN_MENU) {
                    ScreenMenu(
                        navController = navController,
                        viewModel = ScreenMenuViewModel(
                            interactor = getApplicationInstance().interactorImplMainDomain
                        )
                    )
                }

                composable(route = Routes.SCREEN_TREASURY) {
                    ScreenTreasury(
                        navController = navController,
                        viewModel = ScreenTreasuryViewModel(
                            interactor = getApplicationInstance().interactorImplMainDomain
                        )
                    )
                }

                composable(route = Routes.SCREEN_LEVELS) {
                    ScreenLevels(
                        navController = navController,
                        viewModel = ScreenLevelsViewModel()
                    )
                }

                composable(route = Routes.SCREEN_GAME) {
                    ScreenGame(
                        navController = navController,
                        viewModel = ScreenGameViewModel()
                    )
                }

                composable(route = Routes.SCREEN_WIN) {
                    ScreenWin(
                        navController = navController,
                        viewModel = ScreenWinViewModel()
                    )
                }

                composable(route = Routes.SCREEN_LOSE) {
                    ScreenLose(
                        navController = navController,
                        viewModel = ScreenLoseViewModel()
                    )
                }

                composable(route = Routes.SCREEN_HOW_TO_PLAY) {
                    ScreenHowToPlay(
                        navController = navController,
                        viewModel = ScreenHowToPlayViewModel()
                    )
                }
            }
        }
    }
}