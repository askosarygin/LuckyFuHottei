package com.andreykosarygin.game_ui.screen_levels

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.Level
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.game_domain.Interactor
import com.andreykosarygin.game_ui.R
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameLevelFive
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameLevelFour
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameLevelOne
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameLevelThree
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameLevelTwo
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenLevelsViewModel(
    private val interactor: Interactor
) : LuckyFuHotteiViewModel<ScreenLevelsViewModel.Model>(Model()) {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val balance = interactor.getBalance()
            updateBalance(balance.value.toString())

//            interactor.openLevel(Level(0))
//            interactor.openLevel(Level(1))

            model.value.listOfLevels.forEach {
                val isOpen = interactor.levelIsOpen(Level(it.index))
                if (isOpen) {
                    updateLevelByIndex(it.index, it.copy(activated = true))
                }
            }
        }
    }

    fun buttonHomePressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
    }

    fun buttonHowToPlayPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenHowToPlay))
    }

    fun buttonLevelPressed(index: Int) {
        when (index) {
            0 -> updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGameLevelOne))
            1 -> updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGameLevelTwo))
            2 -> updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGameLevelThree))
            3 -> updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGameLevelFour))
            4 -> updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGameLevelFive))
        }
    }

    data class Model(
        val balance: String = "0",
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
                ScreenGameLevelOne,
                ScreenGameLevelTwo,
                ScreenGameLevelThree,
                ScreenGameLevelFour,
                ScreenGameLevelFive,
                ScreenMenu,
                ScreenHowToPlay
            }
        }
    }

    private fun updateLevelByIndex(index: Int, levelInfo: LevelInfo) {
        update {
            it.copy(
                listOfLevels = it.listOfLevels.toMutableList().apply {
                    removeAt(index)
                    add(index, levelInfo)
                }
            )
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