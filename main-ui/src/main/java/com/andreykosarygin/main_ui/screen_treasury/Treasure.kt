package com.andreykosarygin.main_ui.screen_treasury

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.Dp

data class Treasure(
    val index: Int,
    val price: Int,
    val bought: Boolean,
    @DrawableRes val drawableNotBought: Int,
    @DrawableRes val drawableBought: Int,
    val offsetY: Dp
)
