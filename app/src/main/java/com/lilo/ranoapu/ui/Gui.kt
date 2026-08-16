package com.lilo.ranoapu.ui


import android.icu.number.IntegerWidth
import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lilo.ranoapu.engine.Race
import com.lilo.ranoapu.engine.RaceData


@Composable
fun MainScreen() {

    val context = LocalContext.current

	@Composable
	fun createText(textCont: String, fontSize: TextUnit, modifier: Modifier) {

		Text(
			text = textCont,
			color =
				Color(255, 255, 255),

			fontSize = fontSize,
			textAlign = TextAlign.Center,
			modifier = modifier
		)
	}

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

		Box {

			Column(
				modifier = Modifier
					.fillMaxWidth()
					.fillMaxHeight(
						if (RaceData.raceStarted) {
							0.2f
						} else {
							0.1f
						}
					)
					.background(Color(45, 32, 39))

			) {


				if (RaceData.raceStarted) {

					Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Center,
						modifier = Modifier
							.weight(0.1f)
					) {
						val modifier = Modifier
							.wrapContentHeight(
								Alignment.CenterVertically
							)
							.padding(5.dp)
							.weight(0.2f)

						createText(
							"%.2f km/h".format(RaceData.speed),
							15.sp,
							modifier
						)
						createText(
							"%.2fm".format(RaceData.displacement, 0.2f),
							15.sp,
							modifier
						)
						createText(
							"%.2fs".format(RaceData.time),
							15.sp,
							modifier
						)
						createText(
							"%.2fm (dist)".format(RaceData.distance),
							15.sp,
							modifier
						)
						createText(
							"%.2f min/km".format(RaceData.pace),
							15.sp,
							modifier
						)
					}
				}



				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.Center,
					modifier = Modifier
						.weight(0.1f)

				) {



					Button(
						onClick = {
							Race.resetRace()
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
							.fillMaxHeight(0.8f)
							.alpha(if (RaceData.raceStarted) 1f else 0f)


					) {
						createText("Parar", 20.sp,
							Modifier
								.fillMaxWidth()
						)

					}


					Button(
						onClick = {
							Race.toggleRace(context)
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
						createText(
							if (RaceData.manualPause) {
								"Iniciar Corrida"
							} else {
								"Pausar corrida"
							},
							20.sp,
							Modifier

						)
					}

					OutlinedTextField(

						value = "${RaceData.maxDist}",
						onValueChange = { newValue ->
							RaceData.maxDist = newValue.toIntOrNull() ?: 0
						},
						keyboardOptions = KeyboardOptions(
							keyboardType = KeyboardType.Number
						),
						textStyle = TextStyle(
							color = Color(
								255,
								255,
								255
							),
							textAlign = TextAlign.Center,
							fontSize = 20.sp,
						),
						colors = OutlinedTextFieldDefaults.colors(
							focusedBorderColor = Color.Transparent,
							unfocusedBorderColor = Color.Transparent
						),
						label = {
							createText("Distância", 9.sp,
								Modifier
									.fillMaxWidth()
									.wrapContentHeight(
										Alignment.CenterVertically
									)
							)
						},

						modifier = Modifier
							.padding(5.dp)
							.weight(0.25f)
							.fillMaxHeight(0.8f)
							.background(
								Color(72, 0, 135),
								RoundedCornerShape(10.dp)
							)
						)

				}
			}

			@Composable
			fun createPopUp(textCont: String, condition: Boolean) {

				if (condition) {

					createText(
						textCont,
						15.sp,
						Modifier
							.align(Alignment.TopCenter)
							.absoluteOffset(0.dp, -15.dp)
							.background(
								Color(72, 0, 135),
								RoundedCornerShape(10.dp)
							)
							.border(
								width = 2.dp,
								Color(0, 0, 0),
								shape = RoundedCornerShape(10.dp)
							)
							.padding(5.dp)
					)
				}
			}

			createPopUp("EM PAUSA AUTOMÁTICA", RaceData.automaticPause)
			createPopUp("CORRIDA FINALIZADA", RaceData.raceFinished)

		}

    }

}

@Preview(showBackground = true)
@Composable
fun GuiPreview() {
    MainScreen()
}