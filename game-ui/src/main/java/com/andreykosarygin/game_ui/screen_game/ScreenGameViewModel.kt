package com.andreykosarygin.game_ui.screen_game

import androidx.lifecycle.viewModelScope
import com.andreykosarygin.common.LuckyFuHotteiViewModel
import com.andreykosarygin.common.LuckyFuHotteiViewModelSingleLifeEvent
import com.andreykosarygin.game_ui.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenGameViewModel(

) : LuckyFuHotteiViewModel<ScreenGameViewModel.Model>(Model()) {
    private var selectedCellsCount = 0
    private val displayables = listOf(
        Cell.Displayable(iconDrawableId = R.drawable.bear, type = 1),
        Cell.Displayable(iconDrawableId = R.drawable.coins, type = 2),
        Cell.Displayable(iconDrawableId = R.drawable.crane, type = 3),
        Cell.Displayable(iconDrawableId = R.drawable.lamp, type = 4),
        Cell.Displayable(iconDrawableId = R.drawable.panda, type = 5)
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val cells = initCellsWithRandom()
            updateCells(cells)
        }
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

    private fun checkSelectedCells() {
        if (selectedCellsCount == 2) {
            val selectedCells = model.value.cells.filter {
                it.selected
            }

            val firstSelectedCell = selectedCells.first()
            val secondSelectedCell = selectedCells.last()

            cellsAreNeighbors(firstSelectedCell, secondSelectedCell)?.let {
//                Log.i("MY_TAG", "ячейка${it.firstCell.position.id} имеет соседа в направлении ${it.firstCellNeighborhoodDirection.name}")
//                Log.i("MY_TAG", "ячейка${it.secondCell.position.id} имеет соседа в направлении ${it.secondCellNeighborhoodDirection.name}")

                updateCellById(
                    firstSelectedCell.position.id,
                    firstSelectedCell.copy(displayable = it.secondCell.displayable)
                )
                updateCellById(
                    secondSelectedCell.position.id,
                    secondSelectedCell.copy(displayable = it.firstCell.displayable)
                )
            }

            updateCellSelectionById(firstSelectedCell.position.id, false)
            updateCellSelectionById(secondSelectedCell.position.id, false)
            selectedCellsCount = 0


            var winCellsIndexes = checkWinCombinations()

            while (winCellsIndexes.isNotEmpty()) {
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
                winCellsIndexes = checkWinCombinations()
            }
        }
    }

    private fun checkWinCombinations() : List<Int> {
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
            for (index in columnStartIndex .. ((quantityCellsInHeight - 1) * quantityCellsInWidth) + columnStartIndex step quantityCellsInWidth) {
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
        val cells: List<Cell> = listOf(),
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : LuckyFuHotteiViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {

            }
        }
    }

    private fun updateCellSelectionById(id: Int, selected: Boolean) {
        updateCellById(
            id,
            model.value.cells[id - 1].copy(selected = selected)
        )
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

    private fun updateCells(cells: List<Cell>) {
        update {
            it.copy(
                cells = cells
            )
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update {
            it.copy(
                navigationEvent = navigationEvent
            )
        }
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