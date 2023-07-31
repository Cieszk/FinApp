package pl.team.finap.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(
    foreignKeys = [ForeignKey(
        entity = Wallet::class,
        parentColumns = arrayOf("wallet_id"),
        childColumns = arrayOf("wallet_id"),
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "TRANSACTIONS"
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
    BILLS,
    FOOD,
    CLOTHES,
    ELECTRONICS,
    OTHER
}

enum class TransactionType {
    INCOME,
    EXPENSE
}
