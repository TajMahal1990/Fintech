package com.achievemeaalk.freedjf.sms



import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import java.time.ZonedDateTime

@Dao
interface SmsDao {
    @Insert
    suspend fun create(sms: SmsEntity)

    @Transaction
    @Query("UPDATE sms SET send_to_server_at = :sendToServerAt, send_to_server = 1 WHERE uuid = :uuid")
    fun okSendToServer(uuid: String, sendToServerAt: ZonedDateTime)

    @Transaction
    @Query("SELECT * FROM sms ORDER BY created_at DESC")
    fun getAll(): PagingSource<Int, SmsRelationEntity>

    @Transaction
    @Query("SELECT * FROM sms WHERE send_to_server = 0 ORDER BY created_at")
    fun getSendToServer(): List<SmsEntity>
}
