package com.andreykosarygin.game_ui.screen_game

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.BOTTOM
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.LEFT
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.RIGHT
import com.andreykosarygin.game_ui.screen_game.CellsNeighborhood.Direction.TOP

@Composable
fun AnimatedFade(
    visible: Boolean,
    durationMillis: Int = 500,
    finishedListener: ((Float) -> Unit)?,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = durationMillis),
        finishedListener = finishedListener
    )

    Box(
        modifier = Modifier.alpha(alpha = alpha),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun AnimatedOffset(
    playAnimation: Boolean,
    offsetAnimationDp: Dp,
    animationDirection: CellsNeighborhood.Direction,
    durationMillis: Int = 500,
    finishedListener: ((Dp) -> Unit)?,
    content: @Composable () -> Unit
) {
    val animatedDp by animateDpAsState(
        targetValue = if (playAnimation) offsetAnimationDp else 0.dp,
        animationSpec = if (playAnimation) tween(durationMillis = durationMillis) else snap(),
        finishedListener = finishedListener
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.offset(
            x =
            if (playAnimation) {
                when (animationDirection) {
                    LEFT -> -(animatedDp)
                    RIGHT -> animatedDp
                    else -> 0.dp
                }
            } else 0.dp,
            y =
            if (playAnimation) {
                when (animationDirection) {
                    TOP -> -(animatedDp)
                    BOTTOM -> animatedDp
                    else -> 0.dp
                }
            } else 0.dp
        )
    ) {
        content()
    }
}