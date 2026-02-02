package com.achievemeaalk.freedjf.bank


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters

import java.time.ZonedDateTime

@Entity(
    tableName = "bank",
    primaryKeys = ["pk_id"],
    indices = [
        Index(value = ["code"], unique = true),
    ],
)
data class BankEntity(
    @ColumnInfo(name = "pk_id")
    val pkId: Int,
    val name: String,
    val code: String,
    val position: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: ZonedDateTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: ZonedDateTime?,
    @ColumnInfo(name = "is_activated")
    val isActivated: Boolean,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean
)


data class BankRelationEntity(
    @Embedded
    val bankEntity: BankEntity,
    @Relation(
        parentColumn = "pk_id",
        entityColumn = "bank_pk_id",
    )
    val packages: List<BankPackageEntity>,
    @Relation(
        parentColumn = "pk_id",
        entityColumn = "bank_pk_id",
    )
    val numbers: List<BankNumberEntity>
)