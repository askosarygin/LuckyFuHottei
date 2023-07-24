package com.andreykosarygin.main_ui.screen_menu

import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.main_ui.screen_menu.ScreenMenuViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenLevels
import com.andreykosarygin.main_ui.screen_menu.ScreenMenuViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenTreasury

class ScreenMenuViewModel : LuckyFuHotteiViewModel<ScreenMenuViewModel.Model>(Model()) {

    fun buttonStartPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenLevels))
    }

    fun buttonTreasuryPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenTreasury))
    }

    fun buttonMusicPressed() {

    }

    fun buttonSoundPressed() {

    }

    fun buttonProtectPressed() {

    }

    data class Model(
        val balance: String = "530",
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : LuckyFuHotteiViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {
                ScreenLevels,
                ScreenTreasury
            }
        }
    }

    private fun updateBalance(balance: String) {
        update { it.copy(balance = balance) }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}