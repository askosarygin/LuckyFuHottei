package com.andreykosarygin.main_ui.screen_splash

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.main_domain.Interactor
import com.andreykosarygin.main_ui.screen_splash.ScreenSplashViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu
import com.andreykosarygin.main_ui.screen_splash.ScreenSplashViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWelcomeBonus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class ScreenSplashViewModel(
    private val interactor: Interactor
) : LuckyFuHotteiViewModel<ScreenSplashViewModel.Model>(Model()) {

    init {
        viewModelScope.launch {
            val welcomeBonusTime = interactor.loadWelcomeBonusTimer()
            if (Calendar.getInstance().time > welcomeBonusTime) {
                delay(1000L)
                updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenWelcomeBonus))
            } else {
                delay(1000L)
                updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
            }

        }
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
                ScreenWelcomeBonus,
                ScreenMenu
            }
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update {
            it.copy(
                navigationEvent = navigationEvent
            )
        }
    }
}