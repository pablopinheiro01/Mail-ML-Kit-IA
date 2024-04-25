package com.alura.mail.ui.components

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.alura.mail.R
import kotlinx.coroutines.delay

@Composable
fun LoadScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            var mPosition by remember { mutableIntStateOf(3) }
            var aPosition by remember { mutableIntStateOf(2) }
            var lPosition by remember { mutableIntStateOf(1) }
            var iPosition by remember { mutableIntStateOf(0) }

            val mPositionAnimated = animateIntOffsetAsState(
                targetValue = getNextOffset(mPosition),
                label = "mPositionAnimated",
            )

            val aPositionAnimated = animateIntOffsetAsState(
                targetValue = getNextOffset(aPosition),
                label = "lPositionAnimated",
            )

            val iPositionAnimated = animateIntOffsetAsState(
                targetValue = getNextOffset(iPosition),
                label = "mPositionAnimated",
            )

            val lPositionAnimated = animateIntOffsetAsState(
                targetValue = getNextOffset(lPosition),
                label = "lPositionAnimated",
            )

            LaunchedEffect(mPosition) {
                delay(1000)
                mPosition = (mPosition + 1) % 4
                aPosition = (aPosition + 1) % 4
                iPosition = (iPosition + 1) % 4
                lPosition = (lPosition + 1) % 4
            }

            Image(
                painter = painterResource(id = R.drawable.logo_mail_m),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .offset { mPositionAnimated.value }
            )

            Image(
                painter = painterResource(id = R.drawable.logo_mail_i),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .offset { iPositionAnimated.value }
            )

            Image(
                painter = painterResource(id = R.drawable.logo_mail_a),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .offset { aPositionAnimated.value }
            )
            Image(
                painter = painterResource(id = R.drawable.logo_mail_l),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .offset { lPositionAnimated.value }
            )
        }

        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.loading),
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )
    }
}

@Composable
private fun getNextOffset(
    mPosition: Int
): IntOffset {
    val mapOfDirections: List<Pair<String, IntOffset>> = listOf(
        "topStart" to IntOffset(-75, 0),
        "bottomStart" to IntOffset(-75, 150),
        "bottomEnd" to IntOffset(75, 150),
        "topEnd" to IntOffset(75, 0),
    )

    return when (mPosition) {

        0 -> mapOfDirections[1].second
        1 -> mapOfDirections[2].second
        2 -> mapOfDirections[3].second
        3 -> mapOfDirections[0].second
        else -> mapOfDirections[0].second
    }
}