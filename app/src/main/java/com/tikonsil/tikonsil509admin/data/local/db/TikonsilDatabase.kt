package com.tikonsil.tikonsil509admin.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tikonsil.tikonsil509admin.data.local.dao.CostInnoveritDao
import com.tikonsil.tikonsil509admin.data.local.dao.SalesDao
import com.tikonsil.tikonsil509admin.data.local.dao.SalesErrorDao
import com.tikonsil.tikonsil509admin.data.local.entity.CostInnoveritEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesErrorEntity
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.domain.model.Sales

@Database(entities = [SalesEntity::class, SalesErrorEntity::class, CostInnoveritEntity::class], version = 1, exportSchema = false)
abstract class TikonsilDatabase: RoomDatabase() {

    abstract fun salesDao(): SalesDao
    abstract fun salesErrorDao():SalesErrorDao
    abstract fun costInnoveritDao():CostInnoveritDao
}