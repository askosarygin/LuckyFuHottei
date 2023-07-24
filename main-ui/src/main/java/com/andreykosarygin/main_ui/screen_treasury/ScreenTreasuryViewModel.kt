package com.andreykosarygin.main_ui.screen_treasury

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.common.TreasureInfo
import com.andreykosarygin.main_domain.Interactor
import com.andreykosarygin.main_ui.R
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasuryViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasuryViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenTreasuryViewModel(
    private val interactor: Interactor
) : LuckyFuHotteiViewModel<ScreenTreasuryViewModel.Model>(Model()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val balance = interactor.getBalance()
            updateBalance(balance.value.toString())

            model.value.listOfTreasure.forEach {
                val isBought = interactor.isTreasureBought(TreasureInfo(it.index, 0))
                if (isBought) {
                    updateTreasureByIndex(it.index, it.copy(bought = true))
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

    fun treasureSelected(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedTreasure = model.value.listOfTreasure[index]
            if (!selectedTreasure.bought) {
                val bought = interactor.buyTreasure(
                    TreasureInfo(
                        selectedTreasure.index,
                        selectedTreasure.price
                    )
                )

                val balance = interactor.getBalance()
                updateBalance(balance.value.toString())

                if (bought) {
                    updateTreasureByIndex(selectedTreasure.index, selectedTreasure.copy(bought = true))
                }
            }
        }
    }

    data class Model(
        val balance: String = "0",
        val listOfTreasure: List<Treasure> = listOf(
            Treasure(
                0,
                100,
                false,
                R.drawable.screen_treasure_treasure_0_not_bought,
                R.drawable.screen_treasure_treasure_0,
                -(1).dp
            ),
            Treasure(
                1,
                200,
                false,
                R.drawable.screen_treasure_treasure_1_not_bought,
                R.drawable.screen_treasure_treasure_1,
                -(10).dp
            ),
            Treasure(
                2,
                350,
                false,
                R.drawable.screen_treasure_treasure_2_not_bought,
                R.drawable.screen_treasure_treasure_2,
                -(10).dp
            ),
            Treasure(
                3,
                600,
                false,
                R.drawable.screen_treasure_treasure_3_not_bought,
                R.drawable.screen_treasure_treasure_3,
                -(10).dp
            ),
            Treasure(
                4,
                1000,
                false,
                R.drawable.screen_treasure_treasure_4_not_bought,
                R.drawable.screen_treasure_treasure_4,
                10.dp
            ),
            Treasure(
                5,
                2000,
                false,
                R.drawable.screen_treasure_treasure_5_not_bought,
                R.drawable.screen_treasure_treasure_5,
                -(10).dp
            ),
            Treasure(
                6,
                3000,
                false,
                R.drawable.screen_treasure_treasure_6_not_bought,
                R.drawable.screen_treasure_treasure_6,
                0.dp
            ),
            Treasure(
                7,
                3500,
                false,
                R.drawable.screen_treasure_treasure_7_not_bought,
                R.drawable.screen_treasure_treasure_7,
                -(10).dp
            ),
            Treasure(
                8,
                5000,
                false,
                R.drawable.screen_treasure_treasure_8_not_bought,
                R.drawable.screen_treasure_treasure_8,
                -(10).dp
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
                ScreenMenu,
                ScreenHowToPlay
            }
        }
    }

    private fun updateTreasureByIndex(index: Int, treasure: Treasure) {
        update {
            it.copy(
                listOfTreasure = it.listOfTreasure.toMutableList().apply {
                    removeAt(index)
                    add(index, treasure)
                }
            )
        }
    }

    private fun updateBalance(balance: String) {
        update { it.copy(balance = balance) }
    }

    private fun updateListOfTreasure(listOfTreasure: List<Treasure>) {
        update { it.copy(listOfTreasure = listOfTreasure) }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }
}