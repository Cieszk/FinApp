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
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.ChartDataCollection
import pl.team.finap.R

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val purpleToWhiteGradient = Brush.linearGradient(
        colors = listOf(
            Color(R.color.weirdGray),
            Color.Black
        )
    )
    // Replace this with your actual data
    val walletBalance = "100.00"

    val chartDataCollection: ChartDataCollection =
        ChartDataCollection(
            listOf(
                BarData(25f, 0f, Color.Gray),
                BarData(30f, 0f, Color.Green),
                BarData(10f, 0f, Color.Blue)
            )
        )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = purpleToWhiteGradient)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Wallet Icon",
                    tint = Color.Black
                )
                Text(
                    text = walletBalance,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                val data = listOf(10f, 50f, 80f, 40f, 100f, 60f)
                BarChart(data = data, maxHeight = 200.dp)
            }
            Row() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Text(text = "Wydatki")
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Text(text = "Przychody")
                }
            }
            FloatingActionButton(
                onClick = {
                    navController.navigate("Destination")
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add New Transaction")
            }
        }
    }
}


@Composable
fun BarChart(data: List<Float>, maxHeight: Dp) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val maxDataValue = data.maxOrNull() ?: 1f

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),  // Set the color to match Card
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { value ->
                val heightPercentage = value / maxDataValue
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
                            .background(Color.Blue)
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