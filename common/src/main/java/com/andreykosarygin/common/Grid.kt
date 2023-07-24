package com.andreykosarygin.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Grid(
    modifier: Modifier = Modifier,
    quantityCellsInWidth: Int,
    spaceX: Dp = 0.dp,
    spaceY: Dp = 0.dp,
    heightCoefficientRelativeToTheWidthCell: Float = 1f,
    content: @Composable () -> Unit
) {
    Layout(
//        modifier = modifier.fillMaxSize(),
        modifier = modifier,
        measurePolicy = gridMeasurePolicy(
            quantityCellsInWidth = quantityCellsInWidth,
            spaceX = spaceX,
            spaceY = spaceY,
            heightCoefficientRelativeToTheWidthCell = heightCoefficientRelativeToTheWidthCell
        ),
        content = content
    )
}

private fun gridMeasurePolicy(
    quantityCellsInWidth: Int,
    spaceX: Dp,
    spaceY: Dp,
    heightCoefficientRelativeToTheWidthCell: Float
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        val positions = getRelativePositions(
            measurables = measurables,
            constraints = constraints,
            quantityCellsInWidth = quantityCellsInWidth,
            spaceX = spaceX,
            spaceY = spaceY,
            heightCoefficientRelativeToTheWidthCell = heightCoefficientRelativeToTheWidthCell
        )

        var layoutWidth = constraints.minWidth
        var layoutHeight = constraints.minHeight

        if (positions.isNotEmpty()) {
            positions.forEach {
                if (it.positionX + it.placeable.width > layoutWidth) {
                    layoutWidth = it.positionX + it.placeable.width
                }

                if (it.positionY + it.placeable.width > layoutHeight) {
                    layoutHeight = it.positionY + it.placeable.width
                }
            }
        }

        layout(width = layoutWidth, height = layoutHeight) {
            positions.forEach {
                it.placeable.placeRelative(x = it.positionX, y = it.positionY)
            }
        }
    }
}

private fun MeasureScope.getRelativePositions(
    measurables: List<Measurable>,
    constraints: Constraints,
    quantityCellsInWidth: Int,
    spaceX: Dp,
    spaceY: Dp,
    heightCoefficientRelativeToTheWidthCell: Float
): List<PlaceablePosition> {
    val placeablePositions = mutableListOf<PlaceablePosition>()

    val spaceXInPx = spaceX.roundToPx()
    val spaceYInPx = spaceY.roundToPx()

    val singleCellWidthPx =
        (constraints.maxWidth - (spaceXInPx * (quantityCellsInWidth - 1))) / quantityCellsInWidth
    val singleCellHeightPx = (singleCellWidthPx * heightCoefficientRelativeToTheWidthCell).toInt()

    val singleCellConstraints = constraints.copy(
        minWidth = singleCellWidthPx,
        maxWidth = singleCellWidthPx,
        minHeight = singleCellHeightPx,
        maxHeight = singleCellHeightPx
    )

    var x = 0
    var y = 0
    var countOfCellsInLine = 0
    measurables.forEach {
        val placeable = it.measure(singleCellConstraints)

        placeablePositions.add(
            PlaceablePosition(placeable, x, y)
        )

        countOfCellsInLine += 1

        if (countOfCellsInLine < quantityCellsInWidth) {
            x += placeable.width + spaceXInPx
        } else {
            countOfCellsInLine = 0
            x = 0
            y += placeable.height + spaceYInPx
        }
    }
    return placeablePositions
}

private data class PlaceablePosition(
    val placeable: Placeable,
    val positionX: Int,
    val positionY: Int
)