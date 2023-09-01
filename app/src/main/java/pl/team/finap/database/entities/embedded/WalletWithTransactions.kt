package pl.team.finap.database.entities.embedded

import androidx.room.Embedded
import androidx.room.Relation
import pl.team.finap.database.entities.Transactions
import pl.team.finap.database.entities.Wallet

/**
 * Embedded class created according to documentation of
 * Room database to connect two entities in relation one-to-many
 * @author Kamil Cieszkowski
 * @see https://developer.android.com/training/data-storage/room/relationships
 */
data class WalletWithTransactions(
    @Embedded val wallet: Wallet, @Relation(
        parentColumn = "wallet_id", entityColumn = "wallet_id"
    ) val transactions: List<Transactions>
)
