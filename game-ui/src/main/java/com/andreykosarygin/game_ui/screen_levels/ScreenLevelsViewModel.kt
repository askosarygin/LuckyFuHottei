package com.andreykosarygin.game_ui.screen_levels

import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.game_ui.R
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

class ScreenLevelsViewModel : LuckyFuHotteiViewModel<ScreenLevelsViewModel.Model>(Model()) {

    fun buttonHomePressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
    }

    fun buttonHowToPlayPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenHowToPlay))
    }

    fun buttonLevelPressed(index: Int) {

    }

    data class Model(
        val balance: String = "530",
        val listOfLevels: List<LevelInfo> = listOf(
            LevelInfo(
                0,
                true,
                R.drawable.screen_levels_button_level_1_activated,
                R.drawable.screen_levels_button_level_1_activated
            ),
            LevelInfo(
                1,
                true,
                R.drawable.screen_levels_button_level_2_activated,
                R.drawable.screen_levels_button_level_2_activated
            ),
            LevelInfo(
                2,
                false,
                R.drawable.screen_levels_button_level_3_activated,
                R.drawable.screen_levels_button_level_3_not_activated
            ),
            LevelInfo(
                3,
                false,
                R.drawable.screen_levels_button_level_4_activated,
                R.drawable.screen_levels_button_level_4_not_activated
            ),
            LevelInfo(
                4,
                false,
                R.drawable.screen_levels_button_level_5_activated,
                R.drawable.screen_levels_button_level_5_not_activated
            ),
            LevelInfo(
                5,
                false,
                R.drawable.screen_levels_button_level_soon_activated,
                R.drawable.screen_levels_button_level_soon_not_activated
            ),
        ),
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : LuckyFuHotteiViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {
                ScreenMenu,
                ScreenHowToPlay
            }
        }
    }

    private fun updateBalance(balance: String) {
        update { it.copy(balance = balance) }
    }

    private fun updateListOfLevels(listOfLevels: List<LevelInfo>) {
        update { it.copy(listOfLevels = listOfLevels) }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}