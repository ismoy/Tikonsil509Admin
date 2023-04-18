package com.tikonsil.tikonsil509admin.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesErrorEntity
import com.tikonsil.tikonsil509admin.domain.model.Sales
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(sales: List<SalesErrorEntity>)

    @Query("SELECT * FROM SalesErrorEntity ORDER BY firstname ASC")
    fun readData(): Flow<List<SalesErrorEntity>>

    @Query("SELECT * FROM SalesErrorEntity WHERE firstname LIKE:searchQuery OR lastname LIKE:searchQuery OR phone LIKE:searchQuery")
    fun searchDataBase(searchQuery: String): Flow<List<SalesErrorEntity>>

    @Query("DELETE FROM SalesErrorEntity")
    suspend fun deleteAll():Int
}