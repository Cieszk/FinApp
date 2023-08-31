package pl.team.finap.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(
    tableName = "TRANSACTIONS",
    foreignKeys = [ForeignKey(
        entity = Wallet::class,
        parentColumns = ["wallet_id"],
        childColumns = ["wallet_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Transactions(
    @PrimaryKey(autoGenerate = true)
    val transaction_id: Int = 0,
    val wallet_id: Long,
    val amount: BigDecimal,
    val type: TransactionType,
    val transaction_date: Date,
    val description: String,
    val category: CategoryType
)

enum class CategoryType {
    BILLS, FOOD, CLOTHES, ELECTRONICS, OTHER
}

enum class TransactionType {
    INCOME, EXPENSE
}