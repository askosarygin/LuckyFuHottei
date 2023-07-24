package com.andreykosarygin.main_ui.screen_menu

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
import androidx.navigation.NavController
import com.andreykosarygin.common.Balance
import com.andreykosarygin.common.Routes
import com.andreykosarygin.main_ui.R
import com.andreykosarygin.main_ui.screen_menu.ScreenMenuViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenLevels
import com.andreykosarygin.main_ui.screen_menu.ScreenMenuViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenTreasury

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenMenu(ScreenMenuViewModel())
//}

@Composable
fun ScreenMenu(
    navController: NavController,
    viewModel: ScreenMenuViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
        when (route) {
            ScreenTreasury -> navController.navigate(Routes.SCREEN_TREASURY)
            ScreenLevels -> navController.navigate(Routes.SCREEN_LEVELS)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.screen_menu_background),
            contentDescription = stringResource(
                id = com.andreykosarygin.common.R.string.content_description_background
            )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Balance(
                modifier = Modifier.padding(top = 70.dp),
                balanceCount = model.balance
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp)
                    .clickable(
                        onClick = {
                            viewModel.buttonStartPressed()
                        }
                    ),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.screen_menu_button_start),
                contentDescription = stringResource(
                    id = com.andreykosarygin.common.R.string.content_description_background
                )
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
                    .clickable(
                        onClick = {
                            viewModel.buttonTreasuryPressed()
                        }
                    ),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.screen_menu_button_treasury),
                contentDescription = stringResource(
                    id = com.andreykosarygin.common.R.string.content_description_background
                )
            )

            Row(
                modifier = Modifier.padding(bottom = 120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonMusicPressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_menu_icon_music_on),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )

                Image(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonSoundPressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_menu_icon_sound_on),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )

                Image(
                    modifier = Modifier
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonProtectPressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_menu_icon_protect),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )
            }
        }
    }
}