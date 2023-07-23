package com.andreykosarygin.game_ui.screen_game

import androidx.annotation.DrawableRes

data class Cell(
    var selected: Boolean = false,
    var playAnimationFade: Boolean = true,
    var lastCellInFadeAnimation: Boolean = false,
    var playAnimationOffset: Boolean = false,
    var offsetDirection: CellsNeighborhood.Direction = CellsNeighborhood.Direction.LEFT,
    var displayable: Displayable = Displayable(),
    val position: Position
) {
    data class Displayable(
        @DrawableRes var iconDrawableId: Int = 0,
        val type: Int = 0
    )
    data class Position(
        var id: Int = 0,
        var cellOnTheLeftId: Int = 0,
        var cellOnTheRightId: Int = 0,
        var cellOnTheTopId: Int = 0,
        var cellOnTheBottomId: Int = 0
    )
}
