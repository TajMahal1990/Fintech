package com.achievemeaalk.freedjf.bank


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import java.time.ZonedDateTime

@Entity(
    tableName = "bank_trigger",
    indices = [
        Index(value = ["bank_pk_id", "trigger_pk_id"], unique = true),
    ],
)
data class BankTriggerEntity(
    @ColumnInfo(name = "bank_pk_id")
    val bankPkId: Int,
    @ColumnInfo(name = "trigger_pk_id")
    val triggerPkId: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: ZonedDateTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: ZonedDateTime?,
    @ColumnInfo(name = "is_activated")
    val isActivated: Boolean,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean
)
