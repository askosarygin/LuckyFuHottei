package com.andreykosarygin.game_ui.screen_game

import androidx.annotation.DrawableRes

data class Cell(
    var selected: Boolean = false,
    var displayable: Displayable = Displayable(),
    val position: Position
) {
    data class Displayable(
        @DrawableRes var iconDrawableId: Int = 0,
        val type: Int = 0
    )
    data class Position(
        var id: Int,
        var cellOnTheLeftId: Int,
        var cellOnTheRightId: Int,
        var cellOnTheTopId: Int,
        var cellOnTheBottomId: Int
    )
}
