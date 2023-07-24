package com.andreykosarygin.main_ui.screen_welcome_bonus

data class BonusIcon(
    val index: Int,
    var bonusValue: Int = 100,
    val selected: Boolean = false,
    val clickable: Boolean = true
)