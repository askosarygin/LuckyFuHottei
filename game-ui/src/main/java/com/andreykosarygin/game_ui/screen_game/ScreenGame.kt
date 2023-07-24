package com.andreykosarygin.game_ui.screen_game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.andreykosarygin.common.Grid
import com.andreykosarygin.common.OutlinedGoldWhiteText
import com.andreykosarygin.common.R
import com.andreykosarygin.common.Routes
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.BOTTOM
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.LEFT
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.RIGHT
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.TOP
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGame
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenLose
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWin

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenGame(ScreenGameViewModel())
//}

@Composable
fun ScreenGame(
    navController: NavController,
    viewModel: ScreenGameViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
        when (route) {
            ScreenMenu -> navController.navigate(Routes.SCREEN_MENU)
            ScreenGame -> navController.navigate(Routes.SCREEN_LEVELS)
            ScreenHowToPlay -> navController.navigate(Routes.SCREEN_HOW_TO_PLAY)
            ScreenWin -> navController.navigate(Routes.SCREEN_WIN)
            ScreenLose -> navController.navigate(Routes.SCREEN_LOSE)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(
                id = when (model.currentLevelIs){
                    1 -> com.andreykosarygin.game_ui.R.drawable.screen_game_background_level_1
                    2 -> com.andreykosarygin.game_ui.R.drawable.screen_game_background_level_2
                    3 -> com.andreykosarygin.game_ui.R.drawable.screen_game_background_level_3
                    4 -> com.andreykosarygin.game_ui.R.drawable.screen_game_background_level_4
                    5 -> com.andreykosarygin.game_ui.R.drawable.screen_game_background_level_5
                    else -> com.andreykosarygin.game_ui.R.drawable.screen_game_background_level_1
                }
            ),
            contentDescription = stringResource(
                id = R.string.content_description_background
            )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 80.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 140.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(width = 38.dp, 33.dp),
                            painter = painterResource(
                                id = com.andreykosarygin.game_ui.R.drawable.screen_game_earned_points_cup
                            ),
                            contentDescription = stringResource(
                                id = R.string.content_description_background
                            )
                        )

                        OutlinedGoldWhiteText(text = model.targetPoints.toString(), 29.87.sp)
                    }
                }


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Image(
                        modifier = Modifier.size(width = 197.dp, 84.dp),
                        painter = painterResource(
                            id = com.andreykosarygin.game_ui.R.drawable.screen_game_move_limiter_count_background
                        ),
                        contentDescription = stringResource(
                            id = R.string.content_description_background
                        )
                    )

                    OutlinedGoldWhiteText(text = model.moveLimiterCount.toString(), 35.24.sp)
                }

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 10.dp, end = 10.dp),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(
                        id = com.andreykosarygin.game_ui.R.drawable.screen_game_game_field_top
                    ),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 30.dp),
                ) {
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
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 150.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonHomePressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(
                        id = com.andreykosarygin.game_ui.R.drawable.screen_game_icon_home
                    ),
                    contentDescription = stringResource(
                        id = R.string.content_description_background
                    )
                )

                Image(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonRepeatPressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(
                        id = com.andreykosarygin.game_ui.R.drawable.screen_game_icon_repeat
                    ),
                    contentDescription = stringResource(
                        id = R.string.content_description_background
                    )
                )

                Image(
                    modifier = Modifier
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonHowToPlayPressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(
                        id = com.andreykosarygin.game_ui.R.drawable.screen_game_icon_how_to_play
                    ),
                    contentDescription = stringResource(
                        id = R.string.content_description_background
                    )
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
                id = com.andreykosarygin.game_ui.R.drawable.screen_game_background_cell
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
            }
        }
    }
}