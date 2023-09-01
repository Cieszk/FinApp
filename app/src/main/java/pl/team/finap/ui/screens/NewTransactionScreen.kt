package pl.team.finap.ui.screens.viewmodels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.team.finap.R
import pl.team.finap.database.entities.CategoryType
import pl.team.finap.database.entities.TransactionType
import pl.team.finap.database.entities.Transactions
import pl.team.finap.database.respository.TransactionsRepository
import java.math.BigDecimal
import java.util.Date

@Composable
fun NewTransactionScreen(
    transactionsRepository: TransactionsRepository,
    navController: NavController
) {
    var transactionType by remember { mutableStateOf(TransactionType.INCOME) }
    var selectedCategory by remember { mutableStateOf(CategoryType.values().first()) }
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var showError by remember { mutableStateOf(false) }
    val viewModel: TransactionsViewModel =
        viewModel(factory = TransactionsViewModelFactory(transactionsRepository))

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            colorResource(R.color.weirdGray), colorResource(R.color.light_black)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CustomDropdownMenu(items = listOf(
                        TransactionType.INCOME, TransactionType.EXPENSE
                    ),
                        selectedItem = transactionType ?: TransactionType.INCOME,
                        onItemSelected = {
                            transactionType = it
                        })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (transactionType == TransactionType.EXPENSE) {
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        CustomDropdownMenu(items = CategoryType.values().toList(),
                            selectedItem = selectedCategory ?: CategoryType.values().first(),
                            onItemSelected = {
                                selectedCategory = it
                            })
                    }

                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val globalWalletId = viewModel.getGlobalWalletId()

                if (amount.text.isNotBlank() && description.text.isNotBlank()) {
                    val newTransaction = Transactions(
                        wallet_id = globalWalletId,
                        amount = BigDecimal(amount.text),
                        type = transactionType!!,
                        transaction_date = Date(),
                        description = description.text,
                        category = selectedCategory ?: CategoryType.OTHER
                    )
                    viewModel.insertTransaction(newTransaction)
                    viewModel.refreshTransactions()

                    navController.popBackStack()
                } else {
                    showError = true
                }
            }) {
                Text("Add")
            }
            if (showError) {
                Text(
                    "All fields are required!",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun <T> CustomDropdownMenu(
    items: List<T>, selectedItem: T, onItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedItem.toString(), style = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(text = {
                    Text(
                        text = item.toString(), style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ), modifier = Modifier.fillMaxWidth()
                    )
                }, onClick = {
                    onItemSelected(item)
                    expanded = false
                })
            }
        }
    }
}