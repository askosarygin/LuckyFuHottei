package com.andreykosarygin.main_ui.screen_menu

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.main_domain.Interactor
import com.andreykosarygin.main_ui.screen_menu.ScreenMenuViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenLevels
import com.andreykosarygin.main_ui.screen_menu.ScreenMenuViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenTreasury
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenMenuViewModel(
    private val interactor: Interactor
) : LuckyFuHotteiViewModel<ScreenMenuViewModel.Model>(Model()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val balance = interactor.getBalance()
            updateBalance(balance.value.toString())
        }
    }

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
        val balance: String = "0",
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