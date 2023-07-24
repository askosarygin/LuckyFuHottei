package com.andreykosarygin.game_ui.screen_ho_to_play

import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.game_ui.screen_ho_to_play.ScreenHowToPlayViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

class ScreenHowToPlayViewModel : LuckyFuHotteiViewModel<ScreenHowToPlayViewModel.Model>(Model()) {

    fun buttonBackPressed() {
//        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGame))
        //проверяем откуда мы пришли и в зависимости от этого назначаем куда пойти по нажатию кнопки
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
                ScreenMenu
            }
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}