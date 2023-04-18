package com.tikonsil.tikonsil509admin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tikonsil.tikonsil509admin.data.local.entity.CostInnoveritEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CostInnoveritDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(sales: List<CostInnoveritEntity>)

    @Query("SELECT * FROM CostInnoveritEntity ORDER BY formatPrice ASC")
    fun readData(): Flow<List<CostInnoveritEntity>>

    @Query("SELECT * FROM CostInnoveritEntity WHERE operatorName LIKE:searchQuery OR idProduct LIKE:searchQuery OR country LIKE:searchQuery")
    fun searchDataBase(searchQuery: String): Flow<List<CostInnoveritEntity>>

    @Query("DELETE FROM CostInnoveritEntity")
    suspend fun deleteAll():Int
}