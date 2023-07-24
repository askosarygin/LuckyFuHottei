package com.andreykosarygin.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.andreykosarygin.common.ui.theme.White
import com.andreykosarygin.common.ui.theme.Yellow

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Row {
        OutlinedGoldWhiteText(text = "h", fonSize = 44.3.sp)
    }

}

@Composable
fun OutlinedGoldWhiteText(
    text: String,
    fonSize: TextUnit,
    fontFamily: FontFamily = FontFamily(Font(resId = R.font.gang_of_three)),
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        text.forEach {
        Box(contentAlignment = Alignment.TopCenter) {
            BasicText(
                text = it.toString(),
                style = TextStyle.Default.copy(
                    fontFamily = fontFamily,
                    fontSize = fonSize,
                    fontWeight = FontWeight(1000),
                    color = Yellow,
                    textAlign = TextAlign.Center,
                    shadow = Shadow(color = Color.LightGray , Offset(-10f, 10f), 10.0f)
                )
            )
            BasicText(
                text = it.toString(),
                style = TextStyle.Default.copy(
                    fontFamily = fontFamily,
                    fontSize = fonSize,
                    fontWeight = FontWeight(10),
                    color = White,
                    textAlign = TextAlign.Center
                )
            )
        }
        }
    }

}