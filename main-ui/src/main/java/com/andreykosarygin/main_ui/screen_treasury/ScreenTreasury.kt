package com.andreykosarygin.main_ui.screen_treasury

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.andreykosarygin.common.Balance
import com.andreykosarygin.common.Grid
import com.andreykosarygin.common.OutlinedGoldWhiteText
import com.andreykosarygin.common.Routes
import com.andreykosarygin.main_ui.R
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasuryViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenHowToPlay
import com.andreykosarygin.main_ui.screen_treasury.ScreenTreasuryViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenMenu

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenSplash(ScreenTreasuryViewModel())
//}

@Composable
fun ScreenTreasury(
    navController: NavController,
    viewModel: ScreenTreasuryViewModel
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
            painter = painterResource(id = R.drawable.screen_treasure_background),
            contentDescription = stringResource(
                id = com.andreykosarygin.common.R.string.content_description_background
            )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Balance(
                    modifier = Modifier.padding(top = 70.dp),
                    balanceCount = model.balance
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 70.dp, end = 70.dp, top = 20.dp),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.screen_treasure_title),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Grid(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 283.dp),
                quantityCellsInWidth = 3,
                spaceX = 0.dp,
                spaceY = 25.dp
            ) {
                model.listOfTreasure.forEach { treasure ->
                    if (treasure.bought) {
                        Image(
                            modifier = Modifier.fillMaxSize()
                                .offset(y = treasure.offsetY),
                            painter = painterResource(id = treasure.drawableBought),
                            contentDescription = stringResource(
                                id = com.andreykosarygin.common.R.string.content_description_background
                            )
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = treasure.offsetY)
                                    .clickable(
                                        onClick = {
                                            viewModel.treasureSelected(treasure.index)
                                        }
                                    ),
                                painter = painterResource(id = treasure.drawableNotBought),
                                contentDescription = stringResource(
                                    id = com.andreykosarygin.common.R.string.content_description_background
                                )
                            )
                            OutlinedGoldWhiteText(text = treasure.price.toString(), fonSize = 28.sp)
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
                    painter = painterResource(id = R.drawable.screen_treasure_icon_home),
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
                    painter = painterResource(id = R.drawable.screen_treasure_icon_how_to_play),
                    contentDescription = stringResource(
                        id = com.andreykosarygin.common.R.string.content_description_background
                    )
                )
            }
        }
    }

}