package com.achievemeaalk.freedjf.bank


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BankNumberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(bank: BankNumberEntity)

    @Transaction
    @Query("SELECT * FROM bank_number WHERE is_activated = :isActivated AND is_deleted = :isDeleted")
    fun getAll(isActivated: Boolean = true, isDeleted: Boolean = false): PagingSource<Int, BankNumberEntity>

    @Transaction
    @Query("SELECT * FROM bank_number WHERE number = :number AND is_activated = :isActivated AND is_deleted = :isDeleted")
    suspend fun getByNumber(number: String, isActivated: Boolean = true, isDeleted: Boolean = false): BankNumberEntity?
}