package com.andreykosarygin.main_ui.screen_treasury

import androidx.compose.ui.unit.dp
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.main_ui.R
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasuryViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasuryViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

class ScreenTreasuryViewModel : LuckyFuHotteiViewModel<ScreenTreasuryViewModel.Model>(Model()) {

    fun buttonHomePressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
    }

    fun buttonHowToPlayPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenHowToPlay))
    }

    fun treasureSelected(index: Int) {

    }

    data class Model(
        val balance: String = "530",
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
                0,
                200,
                false,
                R.drawable.screen_treasure_treasure_1_not_bought,
                R.drawable.screen_treasure_treasure_1,
                -(10).dp
            ),
            Treasure(
                0,
                350,
                false,
                R.drawable.screen_treasure_treasure_2_not_bought,
                R.drawable.screen_treasure_treasure_2,
                -(10).dp
            ),
            Treasure(
                0,
                600,
                false,
                R.drawable.screen_treasure_treasure_3_not_bought,
                R.drawable.screen_treasure_treasure_3,
                -(10).dp
            ),
            Treasure(
                0,
                1000,
                false,
                R.drawable.screen_treasure_treasure_4_not_bought,
                R.drawable.screen_treasure_treasure_4,
                10.dp
            ),
            Treasure(
                0,
                2000,
                false,
                R.drawable.screen_treasure_treasure_5_not_bought,
                R.drawable.screen_treasure_treasure_5,
                -(10).dp
            ),
            Treasure(
                0,
                3000,
                false,
                R.drawable.screen_treasure_treasure_6_not_bought,
                R.drawable.screen_treasure_treasure_6,
                0.dp
            ),
            Treasure(
                0,
                3500,
                false,
                R.drawable.screen_treasure_treasure_7_not_bought,
                R.drawable.screen_treasure_treasure_7,
                -(10).dp
            ),
            Treasure(
                0,
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