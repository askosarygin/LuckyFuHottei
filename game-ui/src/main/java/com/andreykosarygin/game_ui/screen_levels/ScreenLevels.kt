package com.andreykosarygin.game_ui.screen_levels

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.andreykosarygin.common.Balance
import com.andreykosarygin.common.Routes
import com.andreykosarygin.game_ui.R
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.game_ui.screen_levels.ScreenLevelsViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenLevels(ScreenLevelsViewModel())
//}

@Composable
fun ScreenLevels(
    navController: NavController,
    viewModel: ScreenLevelsViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
        when (route) {
            ScreenMenu -> navController.navigate(Routes.SCREEN_MENU)
            ScreenHowToPlay -> navController.navigate(Routes.SCREEN_HOW_TO_PLAY)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.screen_levels_background),
            contentDescription = stringResource(
                id = com.andreykosarygin.common.R.string.content_description_background
            )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Balance(
                modifier = Modifier.padding(top = 70.dp, start = 100.dp),
                balanceCount = model.balance
            )
        }

        LazyColumn(
            modifier = Modifier.padding(vertical = 150.dp)
        ) {
            items(items = model.listOfLevels) { level ->
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonLevelPressed(level.index)
                            },
                            enabled = level.activated
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(
                        id = if (level.activated) {
                            level.drawableActivated
                        } else {
                            level.drawableNotActivated
                        }
                    ),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )
            }
        }


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 90.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonHomePressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_levels_icon_home),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )

                Image(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonHowToPlayPressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_levels_icon_how_to_play),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )
            }
        }
    }
}