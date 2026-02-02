package com.achievemeaalk.freedjf.database



import com.google.gson.annotations.SerializedName

data class BankDto(
    @SerializedName("pk_id")
    val pkId: Int,
    val bankPkId: Int,
    val code: String,
    val name: String,
    val packages: List<BankPackageDto>,
    val numbers: List<BankNumberDto>,
    val triggers: List<TriggerDto>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("is_activated")
    val isActivated: Boolean,
    @SerializedName("is_deleted")
    val isDeleted: Boolean
)