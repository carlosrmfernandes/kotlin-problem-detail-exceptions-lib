package io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions

import org.springframework.http.HttpStatusCode
import kotlin.Throwable

interface ProblemDetailExceptionInterface {
    val title: String
    val detail: String
    val userTitle: String
    val userMessage: String
    val httpStatus: HttpStatusCode
    val internalCode: String
    val cause: Throwable?
}
