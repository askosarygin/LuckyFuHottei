package com.andreykosarygin.luckyfuhottei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.andreykosarygin.game_ui.screen_game.ScreenGame
import com.andreykosarygin.game_ui.screen_game.ScreenGameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenGame(viewModel = ScreenGameViewModel())
        }
    }
}