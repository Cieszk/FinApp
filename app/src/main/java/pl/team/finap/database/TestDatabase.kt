package pl.team.finap.database

//import androidx.room.Dao
//import androidx.room.Database
//import androidx.room.Entity
//import androidx.room.Insert
//import androidx.room.PrimaryKey
//import androidx.room.Query
//import androidx.room.RoomDatabase
//
//@Database(entities = [TestEntity::class], version = 1)
//abstract class TestDatabase : RoomDatabase() {
//    abstract fun testDao(): TestDao
//}
//
//@Entity
//data class TestEntity(
//    @PrimaryKey val id: Int,
//    val name: String
//)
//
//@Dao
//interface TestDao {
//    @Query("SELECT * FROM TestEntity")
//    fun getAll(): List<TestEntity>
//
//    @Insert
//    fun insert(entity: TestEntity)
//}