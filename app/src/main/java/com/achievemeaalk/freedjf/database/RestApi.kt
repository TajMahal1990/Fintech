package com.achievemeaalk.freedjf.database

/*
import com.bp.antifs.BuildConfig
import com.bp.antifs.currency.LoadCurrencyDto
import com.bp.antifs.deal_in.LoadDealInDto
import com.bp.antifs.deal_out.DealOutDocumentDto
import com.bp.antifs.deal_out.DealOutParsingDto
import com.bp.antifs.deal_out.LoadDealOutDto
import com.bp.antifs.exceptions.DocumentDuplicateException
import com.bp.antifs.exceptions.NoConnectionToServerException
import com.bp.antifs.exceptions.PaymentCannotCreateException
import com.bp.antifs.exceptions.PaymentNotFoundRequisitesException
import com.bp.antifs.pushnotification.PushNotificationEntity
import com.bp.antifs.requisite.LoadRequisiteDto
import com.bp.antifs.sms.SmsEntity
import com.bp.antifs.state.StateService
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.URL
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.concurrent.TimeUnit


data class SmsDto(
    val uuid: String,
    @SerializedName("bank_pk_id")
    val bankPkId: Int,
    val number: String,
    val message: String,
    @SerializedName("created_at")
    val createdAt: String
)

data class AppPushNotificationDto(
    val uuid: String,
    @SerializedName("bank_pk_id")
    val bankPkId: Int,
    val title: String?,
    val text: String?,
    @SerializedName("sub_text")
    val subText: String?,
    @SerializedName("big_text")
    val bigText: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("package_name")
    val packageName: String,
)

data class MessageDto(
    val trigger: String = "sms",
    val sms: SmsDto? = null,
    @SerializedName("app_push_notification")
    val appPushNotification: AppPushNotificationDto? = null
)

data class PaymentCreateDto(
    val amount: Double,
)

data class DealOutChangeStatusDto(
    val status: String,
)

data class DealOutSendDocumentDto(
    @SerializedName("app_version")
    val appVersion: String,
    val content: String,
    val version: String,
    @SerializedName("package_pk_id")
    val packagePkId: Int,
    @SerializedName("ps")
    val signatures: List<String>,
    @SerializedName("fs")
    val fileSignature: String,
    @SerializedName("f")
    val fileEncode: String,
)

data class DealOutRequestConfirmDto(
    @SerializedName("document_pk_id")
    val documentPkId: Int,
)


class RestApi(
    private val apiKey: String?,
) {

    private val urlParseV1 = URL(StateService.BASE_URL_V1)
    private val urlParseV3 = URL(StateService.BASE_URL_V3)

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .callTimeout(10, TimeUnit.SECONDS)
        .build()

    private val client_for_document: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .callTimeout(60, TimeUnit.SECONDS)
        .build()

    fun loadBank() : LoadBankDto? {
        if(apiKey == null) return null
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/bank")
            .build().toUrl()
        println("############################ ${url}")
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                if(!response.isSuccessful) {
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    return null
                }
                val body: String? = response.body?.string()
                val loadBankDto = Gson().fromJson(body, LoadBankDto::class.java)
                return loadBankDto
            }
        } catch (e: IOException) {
            println("############# loadBank exception: ${e.toString()}")
            //Sentry.captureException(e)
            return null
        }
    }

    fun loadRequisite(page: Int, limit: Int) : LoadRequisiteDto? {
        if(apiKey == null) return null
        val offset = page * limit - limit
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/requisite")
            .addQueryParameter("offset", offset.toString())
            .addQueryParameter("limit", limit.toString())
            .build().toUrl()
        println("############################ ${url} ${apiKey}")
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .build()
        println("##################### ${request.toString()}")
        try {
            client.newCall(request).execute().use { response ->
                println("##################### response=${response.toString()} ${response.isSuccessful}")
                if(!response.isSuccessful) {
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("HTTP status code: ${response.code}, URL: $url")
                    return null
                }
                val body: String? = response.body?.string()
                println("$$$$$$$$$$$$$$$$$$$ body = ${body.toString()}")
                val loadRequisiteDto = Gson().fromJson(body, LoadRequisiteDto::class.java)
                return loadRequisiteDto
                return null
            }
        } catch (e: IOException) {
            println("############# loadRequisite exception: ${e.toString()}")
            //Sentry.captureException(e)
            return null
        }
    }

    fun loadCurrency(page: Int, limit: Int) : LoadCurrencyDto? {
        if(apiKey == null) return null
        val offset = page * limit - limit
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/currency")
            .addQueryParameter("offset", offset.toString())
            .addQueryParameter("limit", limit.toString())
            .build().toUrl()
        println("############################ ${url} ${apiKey}")
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .build()
        println("##################### ${request.toString()}")
        try {
            client.newCall(request).execute().use { response ->
                println("##################### response=${response.toString()} ${response.isSuccessful}")
                if(!response.isSuccessful) {
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("HTTP status code: ${response.code}, URL: $url")
                    return null
                }
                val body: String? = response.body?.string()
                println("$$$$$$$$$$$$$$$$$$$ body = ${body.toString()}")
                val loadCurrencyDto = Gson().fromJson(body, LoadCurrencyDto::class.java)
                return loadCurrencyDto
            }
        } catch (e: IOException) {
            println("############# loadCurrencyDto exception: ${e.toString()}")
            //Sentry.captureException(e)
            return null
        }
    }

    fun loadDealIn(page: Int, limit: Int) : LoadDealInDto? {
        if(apiKey == null) return null
        val offset = page * limit - limit
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/deal/in")
            .addQueryParameter("offset", offset.toString())
            .addQueryParameter("limit", limit.toString())
            .build().toUrl()
        println("############################ ${url} ${apiKey}")
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .build()
        println("##################### ${request.toString()}")
        try {
            client.newCall(request).execute().use { response ->
                println("##################### response=${response.toString()} ${response.isSuccessful}")
                if(!response.isSuccessful) {
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("HTTP status code: ${response.code}, URL: $url")
                    return null
                }
                val body: String? = response.body?.string()
                val loadDealInDto = Gson().fromJson(body, LoadDealInDto::class.java)
                return loadDealInDto
            }
        } catch (e: IOException) {
            println("############# loadRequisite exception: ${e.toString()}")
            //Sentry.captureException(e)
            return null
        }
    }

    fun loadDealOut(page: Int, limit: Int) : LoadDealOutDto? {
        if(apiKey == null) return null
        val offset = page * limit - limit
        println("############# loadDealOut page=$page, offset=$offset, limit=$limit")
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/deal/out")
            .addQueryParameter("offset", offset.toString())
            .addQueryParameter("limit", limit.toString())
            .build().toUrl()
        println("############################ ${url} ${apiKey}")
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .build()
        println("##################### ${request.toString()}")
        try {
            client.newCall(request).execute().use { response ->
                println("##################### response=${response.toString()} ${response.isSuccessful}")
                if(!response.isSuccessful) {
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("HTTP status code: ${response.code}, URL: $url")
                    return null
                }
                val body: String? = response.body?.string()
                val loadDealOutDto = Gson().fromJson(body, LoadDealOutDto::class.java)
                return loadDealOutDto
            }
        } catch (e: IOException) {
            println("############# loadDealOutDto exception: ${e.toString()}")
            //Sentry.captureException(e)
            return null
        }
    }

    fun sendSms(smsEntity: SmsEntity): Boolean {
        if(apiKey == null) return false
        val url = HttpUrl.Builder()
            //.scheme("http")
            //.host("192.168.88.87")
            //.port(8000)
            .scheme(urlParseV1.protocol)
            .host(urlParseV1.host)
            .addPathSegments("v3/message")
            .addQueryParameter("verify", StateService.tokenM)
            .build().toUrl()
        println("############################ ${url}")
        val messageDto = MessageDto(
            trigger = "sms",
            sms = SmsDto(
                uuid = smsEntity.uuid,
                bankPkId = smsEntity.bankPkId,
                number = smsEntity.sender,
                message = smsEntity.text,
                createdAt = smsEntity.createdAt.toOffsetDateTime().atZoneSameInstant(ZoneId.of("UTC")).format(
                    DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
                ).toString(),
            )
        )
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(messageDto)
        val requestBody = json.toRequestBody(mediaType)
        println("#@@#@#@##@#@ ${smsEntity.toString()}")
        println("#@@#@#@##@#@ ${json.toString()}")
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .post(requestBody)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                println("CODE=${response.code}")
                if (!response.isSuccessful) {
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")

                    return false
                }
                val body: String? = response.body?.string()
                println("${response.code}=${body}")
            }
            return true
        } catch (e: IOException) {
            println("############# sendSms exception: ${e.toString()}")
            //Sentry.captureException(e)
            return false
        }
    }

    fun sendPush(pushNotificationEntity: PushNotificationEntity): Boolean {
        if(apiKey == null) return false
        val url = HttpUrl.Builder()
            //.scheme("http")
            //.host("192.168.88.87")
            //.port(8000)
            .scheme(urlParseV1.protocol)
            .host(urlParseV1.host)
            .addPathSegments("v3/message")
            .addQueryParameter("verify", StateService.tokenM)
            .build().toUrl()
        println("############################ ${url}")
        val messageDto = MessageDto(
            trigger = "app_push_notification",
            appPushNotification = AppPushNotificationDto(
                uuid = pushNotificationEntity.uuid,
                bankPkId = pushNotificationEntity.bankPkId,
                title = pushNotificationEntity.title,
                text = pushNotificationEntity.text,
                subText = pushNotificationEntity.subText,
                bigText = pushNotificationEntity.bigText,
                createdAt = pushNotificationEntity.createdAt.toOffsetDateTime().atZoneSameInstant(ZoneId.of("UTC")).format(
                    DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
                ).toString(),
                packageName = pushNotificationEntity.packageName
            )
        )
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(messageDto)
        val requestBody = json.toRequestBody(mediaType)
        println("#@@#@#@##@#@ ${pushNotificationEntity.toString()}")
        println("#@@#@#@##@#@ ${json.toString()}")
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .post(requestBody)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                println("CODE=${response.code}")
                if (!response.isSuccessful) {
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    return false
                }
                val body: String? = response.body?.string()
                println("${response.code}=${body}")
            }
            return true
        } catch (e: IOException) {
            println("############# sendPush exception: ${e.toString()}")
            //Sentry.captureException(e)
            return false
        }
    }

    fun createPayment(amount: Double) : PaymentDto? {
        if(apiKey == null) return null
        val paymentCreateDto = PaymentCreateDto(amount = amount)
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/payment")
            .build().toUrl()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(paymentCreateDto)
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .post(requestBody)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                val body: String? = response.body?.string()
                if(!response.isSuccessful) {
                    val restapiCodeDto = Gson().fromJson(body, RestapiCodeDto::class.java)
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("$$$$$$$$$$$$$ ${restapiCodeDto.toString()}")
                    when (restapiCodeDto.messageCode) {
                        "code_401" -> throw PaymentCannotCreateException()
                        "code_402" -> throw PaymentNotFoundRequisitesException()
                    }
                }
                val paymentDto = Gson().fromJson(body, PaymentDto::class.java)
                return paymentDto
            }
        } catch (e: IOException) {
            //Sentry.captureException(e)
            throw NoConnectionToServerException()
        }
    }

    fun dealOutChangeStatus(pkId: Int, status: String) {
        if(apiKey == null) return
        val dealOutChangeStatusDto = DealOutChangeStatusDto(status = status)
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/deal/out/${pkId}/status")
            .build().toUrl()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(dealOutChangeStatusDto)
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .put(requestBody)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                val body: String? = response.body?.string()
                if(!response.isSuccessful) {
                    val restapiCodeDto = Gson().fromJson(body, RestapiCodeDto::class.java)
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("$$$$$$$$$$$$$ ${restapiCodeDto.toString()}")
                    when (restapiCodeDto.messageCode) {
                        "code_401" -> throw PaymentCannotCreateException()
                        "code_402" -> throw PaymentNotFoundRequisitesException()
                    }
                }
            }
        } catch (e: IOException) {
            //Sentry.captureException(e)
            throw NoConnectionToServerException()
        }
    }

    fun dealOutSendDocument(content: String, version: String, packagePkId: Int, signatures: List<String>, fileSignature: String, fileRaw: ByteArray): DealOutDocumentDto? {
        if(apiKey == null) return null
        val dealOutSendDocumentDto = DealOutSendDocumentDto(
            appVersion = BuildConfig.VERSION_NAME,
            content = content,
            version = version,
            packagePkId = packagePkId,
            signatures = signatures,
            fileSignature = fileSignature,
            fileEncode = Base64.getEncoder().encodeToString(fileRaw),
        )
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/deal/out/document")
            .build().toUrl()
        println("############################ ${url}")
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(dealOutSendDocumentDto)
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .post(requestBody)
            .build()
        try {
            client_for_document.newCall(request).execute().use { response ->
                val body: String? = response.body?.string()
                println("##############  ${response.code} BODY ${body.toString()}")
                if(!response.isSuccessful) {
                    val restapiCodeDto = Gson().fromJson(body, RestapiCodeDto::class.java)
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("$$$$$$$$$$$$$ ${restapiCodeDto.toString()}")
                    when (restapiCodeDto.messageCode) {
                        "code_403" -> throw DocumentDuplicateException()
                    }
                }
                val dealOutDocumentDto = Gson().fromJson(body, DealOutDocumentDto::class.java)
                return dealOutDocumentDto
            }
        } catch (e: IOException) {
            //Sentry.captureException(e)
            throw NoConnectionToServerException()
        }
    }

    fun dealOutConfirm(pkId: Int, documentPkId: Int) {
        if(apiKey == null) return
        val dealOutRequestConfirmDto = DealOutRequestConfirmDto(documentPkId = documentPkId)
        val url = HttpUrl.Builder()
            .scheme(urlParseV3.protocol)
            .host(urlParseV3.host)
            .addPathSegments("v3/device/deal/out/${pkId}/confirm")
            .build().toUrl()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(dealOutRequestConfirmDto)
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", StateService.USER_AGENT)
            .addHeader("x-api-key-external-device", apiKey)
            .post(requestBody)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                val body: String? = response.body?.string()
                if(!response.isSuccessful) {
                    val restapiCodeDto = Gson().fromJson(body, RestapiCodeDto::class.java)
                    //Sentry.captureMessage("HTTP status code: ${response.code}, URL: $url")
                    println("$$$$$$$$$$$$$ ${restapiCodeDto.toString()}")
                    when (restapiCodeDto.messageCode) {
                        "code_403" -> throw DocumentDuplicateException()
                    }
                }
            }
        } catch (e: IOException) {
            //Sentry.captureException(e)
            throw NoConnectionToServerException()
        }
    }

}

 */