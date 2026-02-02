package com.achievemeaalk.freedjf.bank




import androidx.paging.PagingSource
import com.bp.antifs.database.LoadBankDto
import com.bp.antifs.database.LocalDatabase
import com.bp.antifs.database.RestApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

interface IBankRepository {
    fun create(data: BankEntity)
    fun getAll(): PagingSource<Int, BankRelationEntity>
    fun loadBanks(): LoadBankDto?
    fun createPackage(data: BankPackageEntity)
    fun createNumber(data: BankNumberEntity)
    fun createPackageNotificationChannel(data: BankPackageNotificationChannelEntity)
    suspend fun getByPackage(name: String): BankPackageEntity?
    suspend fun getByNumber(number: String): BankNumberEntity?
    suspend fun getByChannelName(packagePkId: Int, name: String): BankPackageNotificationChannelEntity?
    suspend fun countChannel(packagePkId: Int): Int
    suspend fun getPackages(): List<BankPackageEntity>
}

class BankRepository(
    private val appDatabase: LocalDatabase,
    private val restApi: RestApi
) : IBankRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun create(data: BankEntity) {
        scope.launch {
            appDatabase.bankDao().create(data)
        }
    }

    override fun createPackage(data: BankPackageEntity) {
        scope.launch {
            appDatabase.bankPackageDao().create(data)
        }
    }

    override fun createNumber(data: BankNumberEntity) {
        scope.launch {
            appDatabase.bankNumberDao().create(data)
        }
    }

    override fun createPackageNotificationChannel(data: BankPackageNotificationChannelEntity) {
        scope.launch {
            appDatabase.bankPackageNotificationChannelDao().create(data)
        }
    }

    override fun getAll(): PagingSource<Int, BankRelationEntity> {
        return appDatabase.bankDao().getAll()
    }

    override fun loadBanks(): LoadBankDto? {
        return restApi.loadBank()
    }


    override suspend fun getByPackage(name: String): BankPackageEntity? {
        return appDatabase.bankPackageDao().getByPackage(name)
    }

    override suspend fun getByNumber(number: String): BankNumberEntity? {
        return appDatabase.bankNumberDao().getByNumber(number)
    }

    override suspend fun getByChannelName(packagePkId: Int, name: String): BankPackageNotificationChannelEntity? {
        return appDatabase.bankPackageNotificationChannelDao().getByChannelName(packagePkId, name)
    }

    override suspend fun countChannel(packagePkId: Int): Int {
        return appDatabase.bankPackageNotificationChannelDao().countChannel(packagePkId)
    }

    override suspend fun getPackages(): List<BankPackageEntity> {
        return appDatabase.bankPackageDao().all()
    }
}