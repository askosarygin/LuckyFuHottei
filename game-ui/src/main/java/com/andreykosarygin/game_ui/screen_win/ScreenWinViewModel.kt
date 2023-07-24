package com.andreykosarygin.game_ui.screen_win

import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameNextLevel
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameRepeatLevel
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

class ScreenWinViewModel : LuckyFuHotteiViewModel<ScreenWinViewModel.Model>(Model()) {

    fun buttonNextLevelPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGameNextLevel))
    }

    fun buttonHomePressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
    }

    fun buttonRepeatPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGameRepeatLevel))
    }

    fun buttonHowToPlayPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenHowToPlay))
    }

    data class Model(
        val earnedPoints: String = "+200",
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : LuckyFuHotteiViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {
                ScreenGameNextLevel,
                ScreenGameRepeatLevel,
                ScreenMenu,
                ScreenHowToPlay
            }
        }
    }

    private fun updateEarnedPoints(earnedPoints: String) {
        update { it.copy(earnedPoints = earnedPoints) }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}