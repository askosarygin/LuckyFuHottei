package com.andreykosarygin.game_ui.screen_win

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.andreykosarygin.common.OutlinedGoldWhiteText
import com.andreykosarygin.common.R
import com.andreykosarygin.common.Routes
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameNextLevel
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameRepeatLevel
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_win.ScreenWinViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenGame(ScreenGameViewModel())
//}

@Composable
fun ScreenWin(

    navController: NavController,
    viewModel: ScreenWinViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
        when (route) {
            ScreenGameNextLevel -> navController.navigate(Routes.SCREEN_LEVELS)
            ScreenMenu -> navController.navigate(Routes.SCREEN_MENU)
            ScreenGameRepeatLevel -> navController.navigate(Routes.SCREEN_LEVELS)
            ScreenHowToPlay -> navController.navigate(Routes.SCREEN_HOW_TO_PLAY)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(
                id = com.andreykosarygin.game_ui.R.drawable.screen_win_background
            ),
            contentDescription = stringResource(
                id = R.string.content_description_background
            )
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 490.dp)
        ) {
            OutlinedGoldWhiteText(
                text = model.earnedPoints,
                fonSize = 35.24.sp
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable(
                        onClick = {
                            viewModel.buttonNextLevelPressed()
                        }
                    ),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(
                    id = com.andreykosarygin.game_ui.R.drawable.screen_win_button_next_level
                ),
                contentDescription = stringResource(
                    id = com.andreykosarygin.common.R.string.content_description_background
                )
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 120.dp)
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
}