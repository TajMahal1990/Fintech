package com.achievemeaalk.freedjf.database


data class LoadBankDto(
    val limit: Int,
    val offset: Int,
    val total: Int,
    val items: List<BankDto>
)