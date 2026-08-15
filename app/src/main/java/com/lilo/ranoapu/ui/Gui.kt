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
import androidx.compose.ui.platform.LocalContext



import com.lilo.ranoapu.engine.Race
import com.lilo.ranoapu.engine.Gps


@Composable
fun MainScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Top,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(0.1f),
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
            modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(0.65f)
        ) {

        }


        Row(
		    modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(0.1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
		) {
			
			Text(
			    text = "Velocidade média: %.2fkm/h".format(Race.speed),
				fontSize = 20.sp,
				modifier = Modifier.weight(0.25f).fillMaxHeight(),
			)
			
			Text(
			    text = "Deslocamento: %.2fm".format(Race.displacement),
				fontSize = 20.sp,
				modifier = Modifier.weight(0.25f).fillMaxHeight(),
			)
			
			Text(
			    text = "Distância: %.2fm".format(Race.distance),
				fontSize = 20.sp,
				modifier = Modifier.weight(0.25f).fillMaxHeight(),
			)
			
			Text(
			    text = "Pace: %.2fmin/km".format(Race.pace),
				fontSize = 20.sp,
				modifier = Modifier.weight(0.25f).fillMaxHeight(),
			)
		}


        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(0.15f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
			
			Text(
			    text = "%.2fs".format(Race.time),
				fontSize = 20.sp,
				modifier = Modifier.weight(0.15f).fillMaxHeight(0.5f),
				
			)
			
			Button(
			    onClick = {
					Race.EndRace()
				},
			    modifier = Modifier.weight(0.15f).fillMaxHeight(0.5f)
				
			) {
				Text(
				    text = "Parar corrida",
				    fontSize = 20.sp,
				)
			}
			
			
            Button(
                onClick = {
                    Race.ToggleRace(context)
                },
				modifier = Modifier.weight(0.5f).fillMaxHeight(0.5f),

            ) {
                Text(
                    text = Race.runBtnMsg,
                    fontSize = 20.sp
                )
            }
			
			
			Text(
			    text = "${Race.maxDist} m",
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