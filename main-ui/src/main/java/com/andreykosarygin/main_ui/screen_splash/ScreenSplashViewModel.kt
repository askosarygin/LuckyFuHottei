package com.andreykosarygin.main_ui.screen_splash

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenSplashViewModel : LuckyFuHotteiViewModel<ScreenSplashViewModel.Model>(Model()) {

    init {
        viewModelScope.launch {
            delay(3000L)

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
                ScreenWelcomeBonus
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