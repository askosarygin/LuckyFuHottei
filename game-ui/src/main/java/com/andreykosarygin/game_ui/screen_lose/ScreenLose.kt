package com.andreykosarygin.game_ui.screen_lose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.andreykosarygin.common.R
import com.andreykosarygin.common.Routes
import com.andreykosarygin.game_ui.screen_lose.ScreenLoseViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameRepeatLevel
import com.andreykosarygin.game_ui.screen_lose.ScreenLoseViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_lose.ScreenLoseViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenGame(ScreenGameViewModel())
//}

@Composable
fun ScreenLose(
    navController: NavController,
    viewModel: ScreenLoseViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
        when (route) {
            ScreenMenu -> navController.navigate(Routes.SCREEN_MENU)
            ScreenGameRepeatLevel -> navController.navigate(Routes.SCREEN_GAME)
            ScreenHowToPlay -> navController.navigate(Routes.SCREEN_HOW_TO_PLAY)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(
                id = com.andreykosarygin.game_ui.R.drawable.screen_lose_background
            ),
            contentDescription = stringResource(
                id = R.string.content_description_background
            )
        )


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 300.dp)
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
                        id = com.andreykosarygin.game_ui.R.drawable.screen_win_icon_home
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
                        id = com.andreykosarygin.game_ui.R.drawable.screen_win_icon_repeat
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
                        id = com.andreykosarygin.game_ui.R.drawable.screen_win_icon_how_to_play
                    ),
                    contentDescription = stringResource(
                        id = R.string.content_description_background
                    )
                )
            }
        }
    }
}