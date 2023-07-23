package com.andreykosarygin.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Balance(balanceCount = "1500")
}

@Composable
fun Balance(
    modifier: Modifier = Modifier,
    balanceCount: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(width = 149.dp, 91.dp),
            painter = painterResource(id = R.drawable.balance_cup),
            contentDescription = stringResource(
                id = R.string.content_description_background
            )
        )


        Row(
            modifier = Modifier.offset(x = -(37).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedGoldWhiteText(text = balanceCount, 44.3.sp)

        }
    }
}