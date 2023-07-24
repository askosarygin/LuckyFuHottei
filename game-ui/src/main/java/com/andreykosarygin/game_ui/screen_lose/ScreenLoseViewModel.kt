package com.andreykosarygin.game_ui.screen_lose

import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.game_ui.screen_lose.ScreenLoseViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameRepeatLevel
import com.andreykosarygin.game_ui.screen_lose.ScreenLoseViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_lose.ScreenLoseViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

class ScreenLoseViewModel : LuckyFuHotteiViewModel<ScreenLoseViewModel.Model>(Model()) {

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
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : LuckyFuHotteiViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {
                ScreenGameRepeatLevel,
                ScreenMenu,
                ScreenHowToPlay
            }
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}