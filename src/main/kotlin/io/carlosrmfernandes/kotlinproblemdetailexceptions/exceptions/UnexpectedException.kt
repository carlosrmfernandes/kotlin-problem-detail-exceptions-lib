package io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

class UnexpectedException(
    httpStatus: HttpStatus? = null,
    cause: Throwable
) : ProblemDetailException(
    title = determineTitle(cause),
    detail = createDetail(cause),
    userTitle = "Ocorreu um erro inesperado",
    userMessage = "Entre em contato com o administrador do sistema",
    httpStatus = httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR,
    internalCode = "UNEXPECTED_ERROR",
    cause = cause
) {
    companion object {
        private fun determineTitle(cause: Throwable): String {
            return if (cause is HttpStatusCodeException && cause.statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
                "Erro Inesperado"
            } else {
                cause.message.orEmpty()
            }
        }

        private fun createDetail(cause: Throwable): String {
            return jacksonObjectMapper().writeValueAsString(
                mapOf(
                    "exception_code" to cause.stackTrace.firstOrNull()?.hashCode(),
                    "exception_line" to cause.stackTrace.firstOrNull()?.lineNumber,
                    "exception_message" to cause.message,
                    "exception_file" to cause.stackTrace.firstOrNull()?.fileName
                )
            )
        }
    }
}

