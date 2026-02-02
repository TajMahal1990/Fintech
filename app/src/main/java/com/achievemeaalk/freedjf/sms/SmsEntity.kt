package com.achievemeaalk.freedjf.sms



import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.bp.antifs.bank.BankEntity
import com.bp.antifs.database.DateTimeConverter
import java.time.ZonedDateTime

@Entity(tableName = "sms")
data class SmsEntity(
    @PrimaryKey
    val uuid: String,
    @ColumnInfo(name = "bank_pk_id")
    val bankPkId: Int,
    val sender: String,
    val text: String,
    @ColumnInfo(defaultValue = "0")
    val slot: Int = 0,
    @ColumnInfo(name = "created_at")
    val createdAt: ZonedDateTime,
    @ColumnInfo(name = "send_to_server")
    val sendToServer: Boolean = false,
    @ColumnInfo(name = "send_to_server_at")
    val sendToServerAt: ZonedDateTime? = null,
    @ColumnInfo(name = "deal_id")
    val dealId: Int? = null
)


data class SmsRelationEntity(
    @Embedded
    val smsEntity: SmsEntity,
    @Relation(
        parentColumn = "bank_pk_id",
        entityColumn = "pk_id",
    )
    val bankEntity: BankEntity
)