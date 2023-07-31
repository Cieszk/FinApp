package pl.team.finap.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "WALLETS")
data class Wallet (
    @PrimaryKey(autoGenerate = true)
    val wallet_id: Long = 0,
    val name: String,
    val balance: BigDecimal
)