package com.achievemeaalk.freedjf.bank


import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.Deferred

@Dao
interface BankPackageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(bank: BankPackageEntity)

    @Transaction
    @Query("SELECT * FROM bank_package WHERE is_activated = :isActivated AND is_deleted = :isDeleted")
    fun getAll(isActivated: Boolean = true, isDeleted: Boolean = false): PagingSource<Int, BankPackageEntity>

    @Transaction
    @Query("SELECT * FROM bank_package WHERE name = :name AND is_activated = :isActivated AND is_deleted = :isDeleted")
    suspend fun getByPackage(name: String, isActivated: Boolean = true, isDeleted: Boolean = false): BankPackageEntity?

    @Transaction
    @Query("SELECT * FROM bank_package WHERE is_activated = :isActivated AND is_deleted = :isDeleted")
    fun all(isActivated: Boolean = true, isDeleted: Boolean = false): List<BankPackageEntity>
}