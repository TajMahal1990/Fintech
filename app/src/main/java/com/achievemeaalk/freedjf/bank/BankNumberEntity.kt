package com.achievemeaalk.freedjf.bank


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import java.time.ZonedDateTime

@Entity(
    tableName = "bank_number",
    primaryKeys = ["pk_id"],
    indices = [
        Index(value = ["number"], unique = true),
        Index(value = ["bank_pk_id", "number"])
    ]
)
data class BankNumberEntity(
    @ColumnInfo(name = "pk_id")
    val pkId: Int,
    @ColumnInfo(name = "bank_pk_id")
    val bankPkId: Int,
    val number: String,
    @ColumnInfo(name = "created_at")
    val createdAt: ZonedDateTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: ZonedDateTime?,
    @ColumnInfo(name = "is_activated")
    val isActivated: Boolean,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean
)