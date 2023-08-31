package pl.team.finap.database

import android.content.Context
import androidx.room.*
import pl.team.finap.database.dao.TransactionsDao
import pl.team.finap.database.dao.WalletDao
import pl.team.finap.database.entities.Transactions
import pl.team.finap.database.entities.Wallet
import pl.team.finap.utils.converters.*

@Database(entities = [Wallet::class, Transactions::class], version = 1, exportSchema = false)
@TypeConverters(
    DateConverter::class,
    EnumCategoryTypeConverter::class,
    EnumTransactionTypeConverter::class,
    BigDecimalConverters::class
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
    abstract fun transactionsDao(): TransactionsDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getDatabase(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}