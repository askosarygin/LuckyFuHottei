package com.andreykosarygin.main_ui.screen_welcome_bonus

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.main_domain.Interactor
import com.andreykosarygin.main_ui.screen_welcome_bonus.ScreenWelcomeBonusViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class ScreenWelcomeBonusViewModel(
    private val interactor: Interactor
) : LuckyFuHotteiViewModel<ScreenWelcomeBonusViewModel.Model>(Model()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val balance = interactor.getBalance()
            updateBalance(balance.value.toString())
        }

        val listOfBonus = model.value.listOfBonuses.shuffled().apply {
            get(0).bonusValue = 0
            get(1).bonusValue = 0
            get(2).bonusValue = 0
            get(3).bonusValue = 0
            get(4).bonusValue = 100
            get(5).bonusValue = 500
            get(6).bonusValue = 1000
            get(7).bonusValue = 2000
            get(8).bonusValue = 5000
        }.sortedBy {
            it.index
        }

        updateListOfBonuses(listOfBonus)
    }

    fun switchBonusIconSelected(index: Int) {
        val selectedBonus = model.value.listOfBonuses[index]

        updateBonusByIndex(index, selectedBonus.copy(selected = !selectedBonus.selected))
        if (!selectedBonus.selected) {
            model.value.listOfBonuses.forEach {
                if (it.index != selectedBonus.index) {
                    updateBonusByIndex(
                        it.index,
                        it.copy(
                            clickable = false
                        )
                    )
                }
            }
            updateSelectedBonus(selectedBonus)
            updateButtonTakeClickable(true)
        } else {
            model.value.listOfBonuses.forEach {
                if (it.index != selectedBonus.index) {
                    updateBonusByIndex(
                        it.index,
                        it.copy(
                            clickable = true
                        )
                    )
                }
            }
            updateSelectedBonus(null)
            updateButtonTakeClickable(false)
        }
    }

    fun buttonTakePressed() {
        updateShowListOfBonus(true)
        viewModelScope.launch(Dispatchers.IO) {
            val selectedBonusValue = model.value.selectedBonus?.bonusValue ?: 0
            interactor.changeBalance(BalanceInfo(selectedBonusValue))
            val balance = interactor.getBalance()
            updateBalance(balance.value.toString())

            interactor.saveWelcomeBonusTimer(
                Date(Calendar.getInstance().timeInMillis + 86400000L)
            )

            delay(3000)
            updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
        }

    }

    data class Model(
        val selectedBonus: BonusIcon? = null,
        val bonusClickable: Boolean = true,
        val buttonTakeClickable: Boolean = false,
        val showListOfBonus: Boolean = false,
        val listOfBonuses: List<BonusIcon> = listOf(
            BonusIcon(index = 0, 100),
            BonusIcon(index = 1, 100),
            BonusIcon(index = 2, 100),
            BonusIcon(index = 3, 100),
            BonusIcon(index = 4, 100),
            BonusIcon(index = 5, 100),
            BonusIcon(index = 6, 100),
            BonusIcon(index = 7, 100),
            BonusIcon(index = 8, 100)
        ),
        val balance: String = "0",
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

    private fun updateSelectedBonus(selectedBonus: BonusIcon?) {
        update { it.copy(selectedBonus = selectedBonus) }
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