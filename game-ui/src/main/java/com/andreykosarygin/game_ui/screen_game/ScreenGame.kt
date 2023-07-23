package com.andreykosarygin.game_ui.screen_game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andreykosarygin.common.Grid
import com.andreykosarygin.common.R
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.BOTTOM
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.LEFT
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.RIGHT
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.TOP

@Composable
fun ScreenGame(
    viewModel: ScreenGameViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
//        when (route) {
//            ScreenTermsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenSignUp ->
//                navController.navigate(Routes.SCREEN_SIGN_UP)
//        }
    }

    Box(contentAlignment = Alignment.Center) {

        Grid(
            quantityCellsInWidth = ScreenGameViewModel.quantityCellsInWidth,
            heightCoefficientRelativeToTheWidthCell = 0.7914f
        ) {
            model.cells.forEach { cell ->
                CellBackground(selected = cell.selected)
            }
        }

        Grid(
            quantityCellsInWidth = ScreenGameViewModel.quantityCellsInWidth,
            heightCoefficientRelativeToTheWidthCell = 0.7914f
        ) {
            val localDensity = LocalDensity.current
            model.cells.forEach { cell ->
                var offsetForAnimation by remember { mutableStateOf(30.dp) }
                CellIcon(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        with(localDensity) {
                            offsetForAnimation = when (cell.offsetDirection) {
                                LEFT -> coordinates.size.width.toDp()
                                RIGHT -> coordinates.size.width.toDp()
                                TOP -> coordinates.size.height.toDp()
                                BOTTOM -> coordinates.size.height.toDp()
                            }
                        }
                    },
                    cellPosition = cell.position.id,
                    selected = cell.selected,
                    drawableId = cell.displayable.iconDrawableId,
                    drawableVisible = cell.playAnimationFade,
                    fadeAnimationFinishedListener =
                    if (cell.lastCellInFadeAnimation) {
                        {
                            viewModel.fadeAnimationFinished()
                        }
                    } else {
                        null
                    },
                    onClickEnabled = model.cellClickEnabled,
                    onClick = {
                        viewModel.cellClicked(cell.position.id)
                    },
                    playOffsetAnimation = cell.playAnimationOffset,
                    offsetAnimationDp = offsetForAnimation,
                    offsetAnimationDirection = cell.offsetDirection,
                    offsetAnimationFinishedListener = {
                        viewModel.cellOffsetAnimationEnded()
                    }
                )
            }
        }
    }
}

@Composable
fun CellBackground(
    modifier: Modifier = Modifier,
    selected: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(
                id = com.andreykosarygin.game_ui.R.drawable.background_cell
            ),
            contentDescription = stringResource(id = R.string.content_description_game_cell),
            colorFilter = if (selected) {
                ColorFilter.tint(Color.Yellow, blendMode = BlendMode.Darken)
            } else {
                null
            }
        )
    }
}

@Composable
fun CellIcon(
    modifier: Modifier = Modifier,
    cellPosition: Int,
    selected: Boolean,
    @DrawableRes drawableId: Int,
    drawableVisible: Boolean,
    playOffsetAnimation: Boolean,
    offsetAnimationDp: Dp,
    offsetAnimationDirection: CellsNeighborhood.Direction,
    offsetAnimationFinishedListener: ((Dp) -> Unit)?,
    fadeAnimationFinishedListener: ((Float) -> Unit)?,
    drawableOffsetY: Dp = 0.dp,
    onClickEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        AnimatedFade(
            visible = drawableVisible,
            finishedListener = fadeAnimationFinishedListener
        ) {
            AnimatedOffset(
                playAnimation = playOffsetAnimation,
                offsetAnimationDp = offsetAnimationDp,
                animationDirection = offsetAnimationDirection,
                finishedListener = offsetAnimationFinishedListener
            ) {
                Image(
                    modifier = Modifier
                        .offset(y = drawableOffsetY)
                        .clickable(
                            onClick = onClick,
                            enabled = onClickEnabled
                        ),
                    painter = painterResource(id = drawableId),
                    contentDescription = stringResource(id = R.string.content_description_game_cell),
                    colorFilter = if (selected) {
                        ColorFilter.tint(Color.Yellow, blendMode = BlendMode.Darken)
                    } else {
                        null
                    }
                )

                Text(
                    text = cellPosition.toString(),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

        }
    }
}