package com.andreykosarygin.main_ui.screen_welcome_bonus

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.main_ui.screen_welcome_bonus.ScreenWelcomeBonusViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenWelcomeBonusViewModel(

) : LuckyFuHotteiViewModel<ScreenWelcomeBonusViewModel.Model>(Model()) {

    fun switchBonusIconSelected(index: Int) {
        val selectedBonus = model.value.listOfBonuses[index]

        updateBonusByIndex(index, selectedBonus.copy(selected = !selectedBonus.selected))
        if (!selectedBonus.selected) {
            updateBonusClickable(false)
            updateButtonTakeClickable(true)
        } else {
            updateBonusClickable(true)
            updateButtonTakeClickable(false)
        }
    }

    fun buttonTakePressed() {
        updateShowListOfBonus(true)
        viewModelScope.launch(Dispatchers.IO) {
            //добавляем бонусы к счету в интерактор и обновляем счет вверху экрана

            delay(3000)
            updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
        }

    }

    data class Model(
        val bonusClickable: Boolean = true,
        val buttonTakeClickable: Boolean = false,
        val showListOfBonus: Boolean = false,
        val listOfBonuses: List<BonusIcon> = listOf(
            BonusIcon(index = 0,100),
            BonusIcon(index = 1,100),
            BonusIcon(index = 2,100),
            BonusIcon(index = 3,100),
            BonusIcon(index = 4,100),
            BonusIcon(index = 5,100),
            BonusIcon(index = 6,100),
            BonusIcon(index = 7,100),
            BonusIcon(index = 8,100)
        ),
        val balance: String = "530",
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

    private fun updateBonusByIndex(index: Int, bonusIcon: BonusIcon) {
        update {
            it.copy(
                listOfBonuses = it.listOfBonuses.toMutableList().apply {
                    removeAt(index)
                    add(index, bonusIcon)
                }
            )
        }
    }

    private fun updateBonusClickable(bonusClickable: Boolean) {
        update { it.copy(bonusClickable = bonusClickable) }
    }

    private fun updateButtonTakeClickable(buttonTakeClickable: Boolean) {
        update { it.copy(buttonTakeClickable = buttonTakeClickable) }
    }

    private fun updateShowListOfBonus(showListOfBonus: Boolean) {
        update { it.copy(showListOfBonus = showListOfBonus) }
    }

    private fun updateListOfBonuses(listOfBonuses: List<BonusIcon>) {
        update { it.copy(listOfBonuses = listOfBonuses) }
    }

    private fun updateBalance(balance: String) {
        update { it.copy(balance = balance) }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}