package com.andreykosarygin.main_ui.screen_welcome_bonus

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andreykosarygin.common.Balance
import com.andreykosarygin.common.Grid
import com.andreykosarygin.common.OutlinedGoldWhiteText
import com.andreykosarygin.main_ui.R

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ScreenWelcomeBonus(
        ScreenWelcomeBonusViewModel()
    )
}

@Composable
fun ScreenWelcomeBonus(
//    navController: NavController,
    viewModel: ScreenWelcomeBonusViewModel
) {
    val model by viewModel.model.collectAsState()

//    model.navigationEvent?.use { route ->
//        when (route) {
//            ScreenMenu -> navController.navigate(Routes.SCREEN_MENU)
//        }
//    }

    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.screen_welcome_bonus_background),
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {


            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 170.dp),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.screen_welcome_bonus_title),
                contentDescription = stringResource(
                    id = com.andreykosarygin.common.R.string.content_description_background
                )
            )

            Grid(
                modifier = Modifier
                    .padding(horizontal = 50.dp, vertical = 30.dp),
                quantityCellsInWidth = 3,
                spaceX = 18.dp,
                spaceY = 18.dp
            ) {
                model.listOfBonuses.forEach { bonus ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (model.showListOfBonus) {
                            OutlinedGoldWhiteText(text = bonus.count.toString(), fonSize = 30.sp)
                        } else {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable(
                                        onClick = {
                                            viewModel.switchBonusIconSelected(bonus.index)
                                        },
                                        enabled = model.bonusClickable
                                    )
                                    .alpha(alpha = if (bonus.selected) 1f else 0.7f),
                                painter = painterResource(id = R.drawable.screen_welcome_bonus_coin),
                                contentDescription = stringResource(id = com.andreykosarygin.common.R.string.content_description_background)
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
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp)
                    .clickable(
                        onClick = {
                            viewModel.buttonTakePressed()
                        },
                        enabled = model.buttonTakeClickable
                    )
                    .alpha(alpha = if (model.buttonTakeClickable) 1f else 0.6f),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = com.andreykosarygin.common.R.drawable.button_take),
                contentDescription = stringResource(
                    id = com.andreykosarygin.common.R.string.content_description_background
                )
            )
        }
    }

}