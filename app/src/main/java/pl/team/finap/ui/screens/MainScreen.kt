package pl.team.finap.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import pl.team.finap.charts.PieChartData

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFAC26FF),
            Color.White
        )
    )

    // Replace this with your actual data
    val walletBalance = "100.00"

    val pieChartDataList = listOf(
        PieChartData(25.0, Brush.linearGradient(listOf(Color.Red, Color.Yellow))),
        PieChartData(50.0, Brush.linearGradient(listOf(Color.Blue, Color.Green))),
        PieChartData(25.0, Brush.linearGradient(listOf(Color.Green, Color.Cyan)))
    )

    Box(
        modifier = Modifier.fillMaxSize().background(brush = backgroundGradient),
        contentAlignment = Alignment.Center
    ) {

        // Wallet State
        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .background(Color.Transparent, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Wallet Icon", tint = Color.Green)
                Text(
                    text = walletBalance,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        // Chart Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Transparent)
        ) {
            PieChart(
                data = pieChartDataList,
                modifier = Modifier.size(200.dp)
            )
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = {
                // Navigate to the transaction screen. Replace "Destination" with your screen's route.
                navController.navigate("Destination")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp) // This adds padding around the button
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add New Transaction")
        }
    }
}

@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val totalValue = data.map { it.value }.sum()
        var startAngle = 0f
        for (chartData in data) {
            val sweepAngle = ((chartData.value / totalValue) * 360f).toFloat()

            // Use the gradient from chartData
            val gradientBrush = chartData.gradient

            drawArc(
                brush = gradientBrush,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = size
            )
            startAngle += sweepAngle
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}