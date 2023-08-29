package pl.team.finap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import pl.team.finap.R

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val purpleToWhiteGradient = Brush.linearGradient(
        colors = listOf(
            Color(R.color.weirdGray), Color(R.color.gradient_end_black)
        )
    )
    val data = listOf(10f, 50f, 80f, 40f, 100f)
    val gradientColors = listOf(
        listOf(
            Color(R.color.gradient_start_red), Color(R.color.gradient_end_pink)
        ), listOf(
            Color(R.color.gradient_start_green), Color(R.color.gradient_end_lime)
        ), listOf(
            Color(R.color.gradient_start_blue), Color(R.color.gradient_end_light_blue)
        ), listOf(
            Color(R.color.gradient_start_yellow), Color(R.color.gradient_end_orange)
        ), listOf(
            Color(R.color.gradient_start_purple), Color(R.color.gradient_end_magenta)
        ), listOf(
            Color(R.color.gradient_start_cyan), Color(R.color.gradient_end_light_cyan)
        )
    )
    val walletBalance = "100.00"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = purpleToWhiteGradient)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Balance", style = TextStyle(
                                fontWeight = FontWeight.Bold, fontSize = 36.sp
                            ), modifier = Modifier.padding(2.dp, 4.dp)
                        )
                    }
                    Row() {
                        Text(
                            text = walletBalance, style = TextStyle(
                                fontStyle = FontStyle.Italic, fontSize = 24.sp
                            ), modifier = Modifier.padding(2.dp, 4.dp)
                        )
                        Text(
                            text = "dollars", style = TextStyle(
                                fontStyle = FontStyle.Italic, fontSize = 24.sp
                            ), modifier = Modifier.padding(2.dp, 4.dp)
                        )
                    }

                }

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                BarChart(data = data, gradientColors = gradientColors, maxHeight = 200.dp)
            }
            TransactionRow()

        }
        FloatingActionButton(
            onClick = {
                navController.navigate("Destination")
            }, modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add New Transaction")
        }
    }
}

@Composable
fun TransactionRow() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TransactionCard(
            label = "Expenses",
            amount = String.format("-%s", "12091"),
            color = Color(R.color.gradient_start_red),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
        TransactionCard(
            label = "Income",
            amount = String.format("+%s", "12091"),
            color = Color(R.color.gradient_start_green),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
    }
}

@Composable
fun TransactionCard(
    label: String,
    amount: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column() {
            Text(
                text = label,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(2.dp, 4.dp)
            )
            Text(
                text = amount,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = color
                ),
                modifier = Modifier.padding(10.dp, 4.dp)
            )
        }
    }
}


@Composable
fun BarChart(data: List<Float>, gradientColors: List<List<Color>>, maxHeight: Dp) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val maxDataValue = data.maxOrNull() ?: 1f

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),  // Set the color to match Card
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEachIndexed { index, value ->
                val heightPercentage = value / maxDataValue
                val barGradient =
                    Brush.verticalGradient(colors = gradientColors[index % gradientColors.size])

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Gray.copy(alpha = 0.2f))
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height((maxHeight * heightPercentage))
                            .background(brush = barGradient)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                        .background(Color.Transparent)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}