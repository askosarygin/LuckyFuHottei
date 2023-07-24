package com.andreykosarygin.game_ui.screen_game

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.common.Level
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.common.NavigationLevelInfo
import com.andreykosarygin.game_domain.Interactor
import com.andreykosarygin.game_ui.R
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGame
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenLose
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenGameViewModel(
    private val argument: NavigationLevelInfo?,
    private val interactor: Interactor
) : LuckyFuHotteiViewModel<ScreenGameViewModel.Model>(Model()) {
    private var selectedCellsCount = 0
    private var winCellsIndexes = mutableListOf<Int>()
    private var cellReplacementIsComing = false
    private var cellOffsetAnimationIsComing = false

    private var firstSelectedCell: Cell = Cell(position = Cell.Position())
    private var secondSelectedCell: Cell = Cell(position = Cell.Position())


    private val displayables = when (argument?.elementsVariations) {
        3 -> listOf(
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_lamp, type = 1),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_coins, type = 2),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_crane, type = 3)
        )

        4 -> listOf(
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_lamp, type = 1),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_coins, type = 2),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_crane, type = 3),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_bear, type = 4)
        )

        5 -> listOf(
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_lamp, type = 1),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_coins, type = 2),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_crane, type = 3),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_bear, type = 4),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_panda, type = 5)
        )

        else -> listOf(
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_lamp, type = 1),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_coins, type = 2),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_crane, type = 3),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_bear, type = 4),
            Cell.Displayable(iconDrawableId = R.drawable.screen_game_icon_panda, type = 5)
        )
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrentLevelIs(argument?.currentLevel ?: 1)

            val cells = initCellsWithRandom()
            updateCells(cells)

            updateMoveLimiterCount(argument?.moveLimiterCount ?: 20)

            delay(1000L)
            winCellsIndexes = checkWinCombinations()
            if (winCellsIndexes.isNotEmpty()) {
                updateCellClickEnabled(false)
                replaceCellsWithAnimation()
            }
        }
    }

    fun buttonHomePressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenMenu))
    }

    fun buttonRepeatPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenGame))
    }

    fun buttonHowToPlayPressed() {
        updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenHowToPlay))
    }

    fun cellClicked(id: Int) {
        val newCellSelection = !model.value.cells[id - 1].selected
        updateCellSelectionById(id, newCellSelection)

        if (newCellSelection) {
            selectedCellsCount += 1
        } else {
            selectedCellsCount -= 1
        }

        checkSelectedCells()
    }

    fun fadeAnimationFinished() {
        if (cellReplacementIsComing) {
            setRandomDisplayablesForWinCells(
                winCellsIndexes
            )

            if (model.value.targetPoints - winCellsIndexes.size < 0) {
                updateTargetPoints(0)
            } else {
                updateTargetPoints(model.value.targetPoints - winCellsIndexes.size)
            }

            switchVisibilityForWinCells(
                true,
                winCellsIndexes
            )

            cellReplacementIsComing = false
        } else {
            activateAnimationFinishedListenerForLastWinCell(
                false,
                winCellsIndexes.last()
            )

            if (model.value.targetPoints <= 0) {
                val openLevelNumber = argument?.openLevelIfWin ?: 2

                viewModelScope.launch(Dispatchers.IO) {
                    interactor.openLevel(Level(openLevelNumber - 1))
                    interactor.changeBalance(BalanceInfo(argument?.prize ?: 50))
                }
                updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenWin))
            }

            winCellsIndexes = checkWinCombinations()

            if (winCellsIndexes.isNotEmpty()) {
                replaceCellsWithAnimation()
            } else {
                updateCellClickEnabled(true)
            }
        }
    }

    private fun replaceCellsWithAnimation() {
        cellReplacementIsComing = true

        activateAnimationFinishedListenerForLastWinCell(
            true,
            winCellsIndexes.last()
        )

        switchVisibilityForWinCells(
            false,
            winCellsIndexes
        )
    }

    private fun setRandomDisplayablesForWinCells(
        winCellsIndexes: List<Int>
    ) {
        winCellsIndexes.forEach {
            val cell = model.value.cells[it]
            val newDisplayable = displayables.random()
            updateCellById(
                cell.position.id,
                cell.copy(
                    displayable = newDisplayable
                )
            )
        }
    }


    private fun switchVisibilityForWinCells(
        visibility: Boolean,
        winCellsIndexes: List<Int>
    ) {
        winCellsIndexes.forEach {
            val cell = model.value.cells[it]

            updateCellById(
                cell.position.id,
                cell.copy(
                    playAnimationFade = visibility
                )
            )
        }
    }

    private fun activateAnimationFinishedListenerForLastWinCell(
        finishedListenerActivated: Boolean,
        lastWinCellId: Int
    ) {
        val lastWinCell = model.value.cells[lastWinCellId]

        updateCellById(
            lastWinCell.position.id,
            lastWinCell.copy(lastCellInFadeAnimation = finishedListenerActivated)
        )
    }

    fun cellOffsetAnimationEnded() {
        if (cellOffsetAnimationIsComing) {
            updateCellById(
                firstSelectedCell.position.id,
                model.value.cells[firstSelectedCell.position.id - 1].copy(
                    displayable = secondSelectedCell.displayable,
                    playAnimationOffset = false
                )
            )

            updateCellById(
                secondSelectedCell.position.id,
                model.value.cells[secondSelectedCell.position.id - 1].copy(
                    displayable = firstSelectedCell.displayable,
                    playAnimationOffset = false
                )
            )
            cellOffsetAnimationIsComing = false

            updateCellClickEnabled(true)

            if (model.value.moveLimiterCount <= 0) {
                updateNavigationEvent(Model.NavigationSingleLifeEvent(ScreenLose))
            }

            winCellsIndexes = checkWinCombinations()
            if (winCellsIndexes.isNotEmpty()) {
                updateCellClickEnabled(false)
                replaceCellsWithAnimation()
            }
        }
    }

    private fun startCellOffsetAnimation(
        cellsNeighborhood: CellsNeighborhood
    ) {
        updateCellClickEnabled(false)
        cellOffsetAnimationIsComing = true

        updateCellById(
            firstSelectedCell.position.id,
            firstSelectedCell.copy(
                playAnimationOffset = true,
                offsetDirection = cellsNeighborhood.firstCellNeighborhoodDirection
            )
        )
        updateCellById(
            secondSelectedCell.position.id,
            secondSelectedCell.copy(
                playAnimationOffset = true,
                offsetDirection = cellsNeighborhood.secondCellNeighborhoodDirection
            )
        )
    }

    private fun checkSelectedCells() {
        if (selectedCellsCount == 2) {
            val selectedCells = model.value.cells.filter {
                it.selected
            }

            firstSelectedCell = selectedCells.first()
            secondSelectedCell = selectedCells.last()

            cellsAreNeighbors(firstSelectedCell, secondSelectedCell)?.let {
                updateMoveLimiterCount(model.value.moveLimiterCount - 1)
                startCellOffsetAnimation(it)
            }

            updateCellSelectionById(firstSelectedCell.position.id, false)
            updateCellSelectionById(secondSelectedCell.position.id, false)
            selectedCellsCount = 0
        }
    }

    private fun checkWinCombinations(): MutableList<Int> {
        val cells = model.value.cells

        val result = mutableListOf<Int>()

        var previousType = 0
        val listOfCurrentWinCombinationsId = mutableListOf<Int>()

        //Проверка всех строк
        for (rowStartIndex in 0..cells.size - quantityCellsInWidth step quantityCellsInWidth) {
            for (index in rowStartIndex until rowStartIndex + quantityCellsInWidth) {
                if (previousType != cells[index].displayable.type) {
                    previousType = cells[index].displayable.type

                    if (listOfCurrentWinCombinationsId.size >= quantityCellsInLineForWin) {
                        result.addAll(listOfCurrentWinCombinationsId)
                    }

                    listOfCurrentWinCombinationsId.clear()
                    listOfCurrentWinCombinationsId.add(index)
                } else {
                    listOfCurrentWinCombinationsId.add(index)
                }
            }
            if (listOfCurrentWinCombinationsId.size >= quantityCellsInLineForWin) {
                result.addAll(listOfCurrentWinCombinationsId)
            }
            previousType = 0
            listOfCurrentWinCombinationsId.clear()
        }

        //Проверка всех столбцов
        for (columnStartIndex in 0 until quantityCellsInWidth) {
            for (index in columnStartIndex..((quantityCellsInHeight - 1) * quantityCellsInWidth) + columnStartIndex step quantityCellsInWidth) {
                if (previousType != cells[index].displayable.type) {
                    previousType = cells[index].displayable.type

                    if (listOfCurrentWinCombinationsId.size >= quantityCellsInLineForWin) {
                        result.addAll(listOfCurrentWinCombinationsId)
                    }

                    listOfCurrentWinCombinationsId.clear()
                    listOfCurrentWinCombinationsId.add(index)
                } else {
                    listOfCurrentWinCombinationsId.add(index)
                }
            }
            if (listOfCurrentWinCombinationsId.size >= quantityCellsInLineForWin) {
                result.addAll(listOfCurrentWinCombinationsId)
            }
            previousType = 0
            listOfCurrentWinCombinationsId.clear()
        }

        return result
    }

    private fun cellsAreNeighbors(firstCell: Cell, secondCell: Cell): CellsNeighborhood? {
        val firstCellId = firstCell.position.id
        if (firstCellId == secondCell.position.cellOnTheTopId) {
            return CellsNeighborhood(
                firstCell,
                CellsNeighborhood.Direction.BOTTOM,
                secondCell,
                CellsNeighborhood.Direction.TOP
            )
        }
        if (firstCellId == secondCell.position.cellOnTheBottomId) {
            return CellsNeighborhood(
                firstCell,
                CellsNeighborhood.Direction.TOP,
                secondCell,
                CellsNeighborhood.Direction.BOTTOM
            )
        }
        if (firstCellId == secondCell.position.cellOnTheLeftId) {
            return CellsNeighborhood(
                firstCell,
                CellsNeighborhood.Direction.RIGHT,
                secondCell,
                CellsNeighborhood.Direction.LEFT
            )
        }
        if (firstCellId == secondCell.position.cellOnTheRightId) {
            return CellsNeighborhood(
                firstCell,
                CellsNeighborhood.Direction.LEFT,
                secondCell,
                CellsNeighborhood.Direction.RIGHT
            )
        }
        return null
    }


    data class Model(
        val currentLevelIs: Int = 1,
        val targetPoints: Int = 100,
        val moveLimiterCount: Int = 0,
        val cellClickEnabled: Boolean = true,
        val cells: List<Cell> = listOf(),
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : LuckyFuHotteiViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {
                ScreenMenu,
                ScreenGame,
                ScreenHowToPlay,
                ScreenWin,
                ScreenLose
            }
        }
    }


    private fun updateCellSelectionById(id: Int, selected: Boolean) {
        updateCellById(id, model.value.cells[id - 1].copy(selected = selected))
    }

    private fun updateCellById(id: Int, newCell: Cell) {
        update {
            it.copy(
                cells = it.cells.toMutableList().apply {
                    removeAt(id - 1)
                    add(id - 1, newCell)
                }
            )
        }
    }

    private fun updateCurrentLevelIs(currentLevelIs: Int) {
        update { it.copy(currentLevelIs = currentLevelIs) }
    }


    private fun updateMoveLimiterCount(moveLimiterCount: Int) {
        update { it.copy(moveLimiterCount = moveLimiterCount) }
    }

    private fun updateTargetPoints(targetPoints: Int) {
        update { it.copy(targetPoints = targetPoints) }
    }

    private fun updateCells(cells: List<Cell>) {
        update { it.copy(cells = cells) }
    }

    private fun updateCellClickEnabled(cellClickEnabled: Boolean) {
        update { it.copy(cellClickEnabled = cellClickEnabled) }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update { it.copy(navigationEvent = navigationEvent) }
    }

    private fun initCellsWithRandom(): MutableList<Cell> {
        val quantityCellsInWidth = ScreenGameViewModel.quantityCellsInWidth
        val quantityCellsInHeight = ScreenGameViewModel.quantityCellsInHeight

        val result = mutableListOf<Cell>()
        val firstCellId = 1
        val quantityOfCells = quantityCellsInWidth * quantityCellsInHeight

        //Добавляем угловые ячейки
        //верхний левый угол
        result.add(
            Cell(
                position = Cell.Position(
                    firstCellId,
                    0,
                    2,
                    0,
                    1 + quantityCellsInWidth
                )
            )
        )
        //верхний правый угол
        result.add(
            Cell(
                position = Cell.Position(
                    quantityCellsInWidth,
                    quantityCellsInWidth - 1,
                    0,
                    0,
                    quantityCellsInWidth + quantityCellsInWidth
                )
            )
        )
        //нижний левый угол
        result.add(
            Cell(
                position = Cell.Position(
                    firstCellId + (quantityCellsInWidth * (quantityCellsInHeight - 1)),
                    0,
                    firstCellId + (quantityCellsInWidth * (quantityCellsInHeight - 1)) + 1,
                    firstCellId + (quantityCellsInWidth * (quantityCellsInHeight - 1)) - quantityCellsInWidth,
                    0
                )
            )
        )
        //нижний правый угол
        result.add(
            Cell(
                position = Cell.Position(
                    quantityOfCells,
                    quantityOfCells - 1,
                    0,
                    quantityOfCells - quantityCellsInWidth,
                    0
                )
            )
        )

        //Добавляем ячейки крайних наружных рядов и столбцов не включая крайние ячейки
        //Верхний ряд
        for (id in 2 until quantityCellsInWidth) {
            result.add(
                Cell(
                    position = Cell.Position(
                        id,
                        id - 1,
                        id + 1,
                        0,
                        id + quantityCellsInWidth
                    )
                )
            )
        }
        //Нижний ряд
        for (id in (quantityOfCells - quantityCellsInWidth + 2) until quantityOfCells) {
            result.add(
                Cell(
                    position = Cell.Position(
                        id,
                        id - 1,
                        id + 1,
                        id - quantityCellsInWidth,
                        0
                    )
                )
            )
        }
        //Левый столбец
        for (i in 1..quantityCellsInHeight - 2) {
            val id = 1 + (quantityCellsInWidth * i)
            result.add(
                Cell(
                    position = Cell.Position(
                        id,
                        0,
                        id + 1,
                        id - quantityCellsInWidth,
                        id + quantityCellsInWidth
                    )
                )
            )
        }
        //Правый столбец
        for (i in 1..quantityCellsInHeight - 2) {
            val id = quantityCellsInWidth + (quantityCellsInWidth * i)
            result.add(
                Cell(
                    position = Cell.Position(
                        id,
                        id - 1,
                        0,
                        id - quantityCellsInWidth,
                        id + quantityCellsInWidth
                    )
                )
            )
        }

        //Добавляем остальные ячейки расположенные в центре
        for (i in 0..quantityCellsInHeight - 3) {
            val startId = ((firstCellId + quantityCellsInWidth + 1) + (quantityCellsInWidth * i))
            val endId =
                ((firstCellId + quantityCellsInWidth + quantityCellsInWidth - 2) + (quantityCellsInWidth * i))
            for (id in startId..endId) {
                result.add(
                    Cell(
                        position = Cell.Position(
                            id,
                            id - 1,
                            id + 1,
                            id - quantityCellsInWidth,
                            id + quantityCellsInWidth
                        )
                    )
                )
            }
        }

        //Рандомно присваиваем каждой ячейке её отображаемое содержимое
        result.shuffle()
        val range = result.size / displayables.size //6
        var from = 0
        var to = range
        displayables.forEachIndexed { indexDisplayable, displayable ->
            for (index in from..to) {
                result[index].displayable = displayable
            }

            if (indexDisplayable == displayables.lastIndex - 1) {
                from = to
                to = result.lastIndex
            } else {
                from = to
                to += range
            }
        }

        //Сортируем результат чтобы все ячейки шли по нумерации
        result.sortBy {
            it.position.id
        }

        return result
    }

    companion object {
        const val quantityCellsInWidth = 5
        const val quantityCellsInHeight = 5
        const val quantityCellsInLineForWin = 3
    }
}