package com.lilo.ranoapu.ui

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


import com.lilo.ranoapu.engine.Race
import com.lilo.ranoapu.engine.RaceData


@Composable
fun MainScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
			.fillMaxSize()
			.background(Color.White),
        verticalArrangement = Arrangement.Top,
    ) {

        Row(
            modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.weight(0.1f),
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
            modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.weight(0.7f)
        ) {

        }


		Column(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.weight(0.2f)
				.background(Color(45, 32, 39))
		) {

			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center,
				modifier = Modifier
					.weight(0.1f)
			) {

				@Composable
				fun createText(textCont: String) {

					return Text(
						text = textCont
							.format(RaceData.speed),

						color =
							Color(255, 255, 255),

						fontSize = 16.sp,
						textAlign = TextAlign.Center,
						modifier = Modifier
							.wrapContentHeight(Alignment.CenterVertically)
							.padding(5.dp)
							.weight(0.2f)
					)
				}

				createText("%.2f km/h"
						.format(RaceData.speed)
				)
				createText("%.2fm"
						.format(RaceData.displacement)
				)
				createText("%.2fs"
						.format(RaceData.time)
				)
				createText("%.2fm (dist)"
						.format(RaceData.distance)
				)
				createText("%.2f min/km"
						.format(RaceData.pace)
				)
			}


			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center,
				modifier = Modifier
					.weight(0.1f)

			) {



				Button(
					onClick = {
						Race.ResetRace()
					},
					shape = RoundedCornerShape(16),
					colors = ButtonDefaults.buttonColors(
						containerColor =
							Color(72, 0, 135)
					),
					contentPadding = PaddingValues(0.dp),

					modifier = Modifier
						.padding(5.dp)
						.weight(0.25f)
						.fillMaxHeight()
						.alpha(if (RaceData.raceStarted) 1f else 1f)


				) {
					Text(
						text = "Parar",
						fontSize = 20.sp,
						textAlign = TextAlign.Center,
						maxLines = 1,
						softWrap = false,
						modifier = Modifier
							.fillMaxWidth()
					)
				}


				Button(
					onClick = {
						Race.ToggleRace(context)
					},
					shape = RoundedCornerShape(30),
					colors = ButtonDefaults.buttonColors(
						containerColor =
							Color(72, 0, 135)
					),
					contentPadding = PaddingValues(0.dp),
					modifier = Modifier
						.padding(5.dp)
						.weight(0.6f)
						.fillMaxHeight(0.9f)


				) {
					Text(
						text = RaceData.runBtnMsg,
						fontSize = 20.sp,
						modifier = Modifier
					)
				}


				Text(
					text = "${RaceData.maxDist}m",
					textAlign = TextAlign.Center,
					fontSize = 20.sp,

					color =
						Color(255, 255, 255),
					modifier = Modifier
						.padding(5.dp)
						.weight(0.2f)
						.fillMaxHeight()
						.background(
							Color(72, 0, 135),
							RoundedCornerShape(10.dp)
						)
						.padding(5.dp)
						.wrapContentHeight(Alignment.CenterVertically)


				)


			}
		}


    }

}

@Preview(showBackground = true)
@Composable
fun GuiPreview() {
    MainScreen()
}