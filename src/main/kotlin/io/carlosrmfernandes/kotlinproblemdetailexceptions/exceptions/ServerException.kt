package io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions

import kotlin.Throwable
import org.springframework.http.HttpStatus

class ServerException(
    httpStatus: HttpStatus? = null,
    cause: Throwable? = null
) : ProblemDetailException(
    title = "Erro interno do servidor",
    detail = "O servidor encontrou um erro interno e não foi capaz de completar sua requisição",
    userTitle = "Erro interno do servidor",
    userMessage = "O servidor encontrou um erro interno e não foi capaz de completar sua requisição.",
    httpStatus = httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR,
    internalCode = "UNEX0001",
    cause = cause
)

