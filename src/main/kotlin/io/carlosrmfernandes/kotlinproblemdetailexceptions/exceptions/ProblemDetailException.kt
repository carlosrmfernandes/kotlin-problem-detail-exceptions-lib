package io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions

import io.carlosrmfernandes.kotlinproblemdetailexceptions.config.AppConfig
import io.carlosrmfernandes.kotlinproblemdetailexceptions.enums.ExceptionsFieldsEnum
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode

abstract class ProblemDetailException(
    override val title: String,
    override val detail: String,
    override val userTitle: String,
    override val userMessage: String,
    override val httpStatus: HttpStatusCode,
    override val internalCode: String,
    override val cause: Throwable? = null
) : Exception(title, cause), ProblemDetailExceptionInterface {

    private val instance: String = this::class.simpleName ?: "Unknown"
    private val logAppName: String = AppConfig.appName
    private val logThrow: Boolean = AppConfig.logThrow

    init {
        if (logThrow) {
            LoggerFactory.getLogger(this::class.java).error(
                "[$logAppName][$internalCode] ${toMap()}"
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            ExceptionsFieldsEnum.TYPE.value to instance,
            ExceptionsFieldsEnum.TITLE.value to title,
            ExceptionsFieldsEnum.STATUS.value to httpStatus.value(),
            ExceptionsFieldsEnum.DETAIL.value to detail,
            ExceptionsFieldsEnum.INTERNAL_CODE.value to internalCode,
            ExceptionsFieldsEnum.MESSAGE.value to message,
            ExceptionsFieldsEnum.USER_MESSAGE.value to userMessage,
            ExceptionsFieldsEnum.USER_TITLE.value to userTitle,
            ExceptionsFieldsEnum.LOCATION.value to "${this::class.simpleName}:${Thread.currentThread().stackTrace[1].lineNumber}",
            ExceptionsFieldsEnum.TRACE_ID.value to "trace_id_value",
            ExceptionsFieldsEnum.PREVIOUS_MESSAGE.value to cause?.message,
            ExceptionsFieldsEnum.PREVIOUS_TYPE.value to cause?.javaClass?.simpleName,
            ExceptionsFieldsEnum.PREVIOUS_CODE.value to cause?.stackTrace?.firstOrNull()?.hashCode(),
            ExceptionsFieldsEnum.PREVIOUS_LOCATION.value to "${cause?.stackTrace?.firstOrNull()?.fileName}:${cause?.stackTrace?.firstOrNull()?.lineNumber}"
        )
    }
}