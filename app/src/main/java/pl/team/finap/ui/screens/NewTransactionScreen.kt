package pl.team.finap.ui.screens

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
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.team.finap.R
import pl.team.finap.database.entities.CategoryType
import pl.team.finap.database.entities.TransactionType

@Composable
fun NewTransactionScreen() {
    var transactionType by remember { mutableStateOf<TransactionType?>(null) }
    var selectedCategory by remember { mutableStateOf<CategoryType?>(null) }
    var amount by remember { mutableStateOf(TextFieldValue("")) }

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

            // TransactionType dropdown inside a Card
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CustomDropdownMenu(items = listOf(TransactionType.INCOME, TransactionType.EXPENSE),
                        selectedItem = transactionType ?: TransactionType.INCOME,
                        onItemSelected = {
                            transactionType = it
                        })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (transactionType == TransactionType.EXPENSE) {
                Spacer(modifier = Modifier.height(16.dp))

                // CategoryType dropdown inside a Card
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
                OutlinedTextField(value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }

            Button(onClick = { /* Handle transaction creation */ }) {
                Text("Add")
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

@Composable
@Preview(showBackground = true)
fun NewTransactionScreenPreview() {
    NewTransactionScreen()
}
