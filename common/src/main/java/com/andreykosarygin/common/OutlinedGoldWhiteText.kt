package com.andreykosarygin.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.andreykosarygin.common.ui.theme.White
import com.andreykosarygin.common.ui.theme.Yellow

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Row {
        OutlinedGoldWhiteText(text = "1500", fonSize = 44.3.sp)
    }

}

@Composable
fun OutlinedGoldWhiteText(
    text: String,
    fonSize: TextUnit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        text.forEach {
        Box(contentAlignment = Alignment.TopCenter) {
            BasicText(
                text = it.toString(),
                style = TextStyle.Default.copy(
                    fontFamily = FontFamily(Font(resId = R.font.gang_of_three)),
                    fontSize = fonSize,
                    fontWeight = FontWeight(1000),
                    color = Yellow,
                    shadow = Shadow(color = Color.LightGray , Offset(-10f, 10f), 10.0f)
                )
            )
            BasicText(
                text = it.toString(),
                style = TextStyle.Default.copy(
                    fontFamily = FontFamily(Font(resId = R.font.gang_of_three)),
                    fontSize = fonSize,
                    fontWeight = FontWeight(10),
                    color = White
                )
            )
        }
        }
    }

}