package com.andreykosarygin.game_ui.screen_ho_to_play

import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.game_ui.screen_ho_to_play.ScreenHowToPlayViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.BackPressed
import com.andreykosarygin.game_ui.screen_ho_to_play.ScreenHowToPlayViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

class ScreenHowToPlayViewModel : LuckyFuHotteiViewModel<ScreenHowToPlayViewModel.Model>(Model()) {

    fun buttonBackPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(BackPressed))
    }

    fun buttonHomePressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
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
                ScreenMenu,
                BackPressed
            }
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}