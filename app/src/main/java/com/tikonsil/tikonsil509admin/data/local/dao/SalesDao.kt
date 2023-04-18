package com.tikonsil.tikonsil509admin.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.domain.model.Sales
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(sales: List<SalesEntity>)

    @Query("SELECT * FROM SalesEntity ORDER BY firstname ASC")
    fun readData(): Flow<List<SalesEntity>>

    @Query("SELECT * FROM SalesEntity WHERE firstname LIKE:searchQuery OR lastname LIKE:searchQuery OR phone LIKE:searchQuery")
    fun searchDataBase(searchQuery: String): Flow<List<SalesEntity>>

    @Query("DELETE FROM SalesEntity")
    suspend fun deleteAll():Int
}