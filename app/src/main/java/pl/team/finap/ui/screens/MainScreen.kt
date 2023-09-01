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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.team.finap.R
import pl.team.finap.database.entities.CategoryType
import pl.team.finap.database.respository.TransactionsRepository
import pl.team.finap.database.respository.WalletRepository
import pl.team.finap.ui.screens.viewmodels.TransactionsViewModel
import pl.team.finap.ui.screens.viewmodels.TransactionsViewModelFactory
import pl.team.finap.ui.screens.viewmodels.WalletViewModel
import pl.team.finap.ui.screens.viewmodels.WalletViewModelFactory


@Composable
fun MainScreen(
    navController: NavController,
    transactionsRepository: TransactionsRepository,
    walletRepository: WalletRepository
) {
    val transactionsViewModel: TransactionsViewModel =
        viewModel(factory = TransactionsViewModelFactory(transactionsRepository))
    val walletViewModel: WalletViewModel =
        viewModel(factory = WalletViewModelFactory(walletRepository))

    val walletBalance: Float? by walletViewModel.walletBalance.observeAsState()
    val totalIncome by transactionsViewModel.totalIncome.observeAsState(initial = 0f)
    val totalExpenses by transactionsViewModel.totalExpenses.observeAsState(initial = 0f)

    val categoryTotals by transactionsViewModel.expensePerCategory.collectAsState(emptyList())
    val categoryAmounts = categoryTotals.map { it.total }.toList()

    val purpleToWhiteGradient = Brush.linearGradient(
        colors = listOf(
            colorResource(R.color.weirdGray), colorResource(R.color.light_black)
        )
    )
    val data = listOf(10f, 50f, 80f, 40f, 100f)
    val gradientColors = listOf(
        listOf(
            colorResource(id = R.color.gradient_start_red),
            colorResource(id = R.color.gradient_end_pink)
        ), listOf(
            colorResource(id = R.color.gradient_start_green),
            colorResource(id = R.color.gradient_end_lime)
        ), listOf(
            colorResource(id = R.color.gradient_start_blue),
            colorResource(id = R.color.gradient_end_light_blue)
        ), listOf(
            colorResource(id = R.color.gradient_start_yellow),
            colorResource(id = R.color.gradient_end_orange)
        ), listOf(
            colorResource(id = R.color.gradient_start_purple),
            colorResource(id = R.color.gradient_end_magenta)
        ), listOf(
            colorResource(id = R.color.gradient_start_cyan),
            colorResource(id = R.color.gradient_end_light_cyan)
        )
    )

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
                            text = walletBalance.toString(), style = TextStyle(
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
                BarChart(
                    data = categoryAmounts, gradientColors = gradientColors, maxHeight = 200.dp
                )
            }
            LegendCard(categories = CategoryType.values().toList())
            TransactionRow(income = totalIncome, expense = totalExpenses)

        }
        FloatingActionButton(
            onClick = {
                navController.navigate("NewTransaction")
            }, modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add New Transaction")
        }
    }
}

@Composable
fun TransactionRow(income: Float, expense: Float) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TransactionCard(
            label = "Expenses",
            amount = String.format("-%s", expense.toString()),
            color = colorResource(id = R.color.gradient_start_red),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
        TransactionCard(
            label = "Income",
            amount = String.format("+%s", income.toString()),
            color = colorResource(id = R.color.gradient_start_green),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
    }
}

@Composable
fun TransactionCard(
    label: String, amount: String, color: Color, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column() {
            Text(
                text = label, style = TextStyle(
                    fontWeight = FontWeight.Bold, fontSize = 18.sp
                ), modifier = Modifier.padding(2.dp, 4.dp)
            )
            Text(
                text = amount, style = TextStyle(
                    fontWeight = FontWeight.Bold, fontSize = 24.sp, color = color
                ), modifier = Modifier.padding(10.dp, 4.dp)
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
                .background(color = MaterialTheme.colorScheme.surface),
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

@Composable
fun LegendCard(categories: List<CategoryType>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            categories.forEach { category ->
                val (startColorRes, endColorRes) = getCategoryGradientColors(category)
                val startColor = colorResource(id = startColorRes)
                val endColor = colorResource(id = endColorRes)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        startColor, endColor
                                    )
                                )
                            )
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = category.name)
                }
            }
        }
    }
}

@Composable
fun getCategoryGradientColors(category: CategoryType): Pair<Int, Int> {
    return when (category) {
        CategoryType.BILLS -> Pair(
            R.color.gradient_start_red, R.color.gradient_end_pink
        )

        CategoryType.FOOD -> Pair(
            R.color.gradient_start_green, R.color.gradient_end_lime
        )

        CategoryType.CLOTHES -> Pair(
            R.color.gradient_start_blue, R.color.gradient_end_light_blue
        )

        CategoryType.ELECTRONICS -> Pair(
            R.color.gradient_start_yellow, R.color.gradient_end_orange
        )

        CategoryType.OTHER -> Pair(
            R.color.gradient_start_purple, R.color.gradient_end_magenta
        )
    }
}
