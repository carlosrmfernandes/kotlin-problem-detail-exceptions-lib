package io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions

import org.springframework.http.HttpStatus

class ClientException(
    httpStatus: HttpStatus? = null,
    cause: Throwable? = null,
) : ProblemDetailException(
    title = "Erro de cliente na requisição",
    detail = "O servidor não foi capaz de processar a requisição por causa de algo que é percebido como um erro do cliente",
    userTitle = "Erro na requisição",
    userMessage = "Não foi possível processar a solicitação. Tente novamente. Se o problema persistir, entre em contato com o suporte.",
    httpStatus = httpStatus ?: HttpStatus.BAD_REQUEST,
    internalCode = "CLIE0001",
    cause = cause
)
