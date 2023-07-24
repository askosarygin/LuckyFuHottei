package com.andreykosarygin.game_ui.screen_levels

import androidx.annotation.DrawableRes

data class LevelInfo(
    val index: Int,
    val activated: Boolean,
    @DrawableRes val drawableActivated: Int,
    @DrawableRes val drawableNotActivated: Int
)
