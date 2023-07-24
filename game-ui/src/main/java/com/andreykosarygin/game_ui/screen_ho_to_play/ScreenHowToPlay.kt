package com.andreykosarygin.game_ui.screen_ho_to_play

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.andreykosarygin.common.OutlinedGoldWhiteText
import com.andreykosarygin.common.Routes
import com.andreykosarygin.game_ui.R
import com.andreykosarygin.game_ui.screen_ho_to_play.ScreenHowToPlayViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.BackPressed
import com.andreykosarygin.game_ui.screen_ho_to_play.ScreenHowToPlayViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenHowToPlay(ScreenHowToPlayViewModel())
//}

@Composable
fun ScreenHowToPlay(
    navController: NavController,
    viewModel: ScreenHowToPlayViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
        when (route) {
            ScreenMenu -> navController.navigate(Routes.SCREEN_MENU)
            BackPressed -> navController.navigate(Routes.SCREEN_MENU)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.screen_how_to_play_background),
            contentDescription = stringResource(
                id = com.andreykosarygin.common.R.string.content_description_background
            )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            OutlinedGoldWhiteText(
                modifier = Modifier.padding(top = 140.dp),
                text = stringResource(id = com.andreykosarygin.common.R.string.how_to_play),
                fonSize = 43.54.sp
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedGoldWhiteText(
                    text = stringResource(
                        id = com.andreykosarygin.common.R.string.how_to_play_description_1_1
                    ),
                    fonSize = 25.sp,
                    fontFamily = FontFamily(
                        Font(
                            resId = com.andreykosarygin.common.R.font.geminu_libre
                        )
                    )
                )
                OutlinedGoldWhiteText(
                    text = stringResource(
                        id = com.andreykosarygin.common.R.string.how_to_play_description_1_2
                    ),
                    fonSize = 25.sp,
                    fontFamily = FontFamily(
                        Font(
                            resId = com.andreykosarygin.common.R.font.geminu_libre
                        )
                    )
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 30.dp)
                        .offset(x = -(20).dp),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_how_to_play_three_matches),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )

                OutlinedGoldWhiteText(
                    text = stringResource(
                        id = com.andreykosarygin.common.R.string.how_to_play_description_2_1
                    ),
                    fonSize = 25.sp,
                    fontFamily = FontFamily(
                        Font(
                            resId = com.andreykosarygin.common.R.font.geminu_libre
                        )
                    )
                )

                OutlinedGoldWhiteText(
                    text = stringResource(
                        id = com.andreykosarygin.common.R.string.how_to_play_description_2_2
                    ),
                    fonSize = 25.sp,
                    fontFamily = FontFamily(
                        Font(
                            resId = com.andreykosarygin.common.R.font.geminu_libre
                        )
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
                modifier = Modifier.padding(bottom = 150.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(width = 46.dp, height = 48.dp)
                        .clickable(
                            onClick = {
                                viewModel.buttonBackPressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_how_to_play_icon_back),
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
                                viewModel.buttonHomePressed()
                            }
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_how_to_play_icon_home),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )
            }
        }
    }

}