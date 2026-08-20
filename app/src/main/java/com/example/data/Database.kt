package com.example.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val passwordHash: String
)

@Entity(tableName = "food_donations")
data class FoodDonation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val businessName: String,
    val businessType: String,
    val volume: String,
    val town: String,
    val dietaryInfo: String,
    val isClaimed: Boolean = false,
    val claimStatus: String = "Available",
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
}

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_donations ORDER BY isClaimed ASC, timestamp DESC")
    fun getAllDonations(): Flow<List<FoodDonation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDonation(donation: FoodDonation)

    @Query("UPDATE food_donations SET isClaimed = 1, claimStatus = :status WHERE id = :id")
    suspend fun claimDonation(id: Int, status: String)

    @Query("SELECT COUNT(*) FROM food_donations")
    suspend fun getCount(): Int
}

@Database(entities = [FoodDonation::class, User::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun userDao(): UserDao
}

class AppRepository(
    private val foodDao: FoodDao,
    private val userDao: UserDao
) {
    val allDonations: Flow<List<FoodDonation>> = foodDao.getAllDonations()

    suspend fun insert(donation: FoodDonation) = foodDao.insertDonation(donation)

    suspend fun claim(id: Int, status: String) = foodDao.claimDonation(id, status)
    
    suspend fun getCount() = foodDao.getCount()

    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)

    suspend fun insertUser(user: User) = userDao.insertUser(user)
}
