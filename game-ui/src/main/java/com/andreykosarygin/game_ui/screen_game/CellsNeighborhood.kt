package com.andreykosarygin.game_ui.screen_game

data class CellsNeighborhood(
    val firstCell: Cell,
    val firstCellNeighborhoodDirection: Direction,
    val secondCell: Cell,
    val secondCellNeighborhoodDirection: Direction
) {
    enum class Direction {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }
}
