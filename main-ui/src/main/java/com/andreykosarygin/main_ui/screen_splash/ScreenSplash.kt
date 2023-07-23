package com.andreykosarygin.main_ui.screen_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.andreykosarygin.common.Routes
import com.andreykosarygin.main_ui.R
import com.andreykosarygin.main_ui.screen_splash.ScreenSplashViewModel.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWelcomeBonus

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    ScreenSplash()
//}

@Composable
fun ScreenSplash(
    navController: NavController,
    viewModel: ScreenSplashViewModel
) {
    val model by viewModel.model.collectAsState()

    model.navigationEvent?.use { route ->
        when (route) {
            ScreenWelcomeBonus -> navController.navigate(Routes.SCREEN_WELCOME_BONUS)
        }
    }

    Image(
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.splash_background),
        contentDescription = stringResource(
            id = com.andreykosarygin.common.R.string.content_description_background
        )
    )
}