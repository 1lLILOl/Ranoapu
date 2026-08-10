package com.lilo.ranoapu.ui

import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


import com.lilo.engine.Race


@Composable
fun MainScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Top,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                color = Color(0xFF140038),
                text = "Ranoapu",
                fontSize = 50.sp,
            )
        }



        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f)
        ) {

        }



        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
			
			Text(
			    text = "%.2fs".format(Race.time),
				fontSize = 20.sp,
				modifier = Modifier.weight(0.2f).fillMaxHeight(0.5f),
				
			)
			
            Button(
                onClick = {
                    Race.TryStartRace(context)
                },
				modifier = Modifier.weight(0.6f).fillMaxHeight(0.5f),

            ) {
                Text(
                    text = "Iniciar corrida",
                    fontSize = 20.sp
                )
            }
			
			
			Text(
			    text = "${Race.dist} m",
				fontSize = 20.sp,
				modifier = Modifier.weight(0.2f).fillMaxHeight(0.5f),
				
			)
			
			
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GuiPreview() {
    MainScreen()
}