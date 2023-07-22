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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andreykosarygin.common.R

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Cell(
        drawableId = com.andreykosarygin.game_ui.R.drawable.coins,
        onClick = {},
        cellPosition = 0
    )
}

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

    Grid(
        quantityCellsInWidth = ScreenGameViewModel.quantityCellsInWidth,
        heightCoefficientRelativeToTheWidthCell = 0.7914f
    ) {
        model.cells.forEach { cell ->
            Cell(
                cellPosition = cell.position.id,
                selected = cell.selected,
                drawableId = cell.displayable.iconDrawableId,
                onClick = {
                    viewModel.cellClicked(cell.position.id)
                }
            )
        }
    }

}

@Composable
fun Cell(
    cellPosition: Int,
    selected: Boolean = false,
    @DrawableRes drawableId: Int,
    drawableOffsetY: Dp = 0.dp,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable(onClick = onClick)
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

        Image(
            modifier = Modifier.offset(y = drawableOffsetY),
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