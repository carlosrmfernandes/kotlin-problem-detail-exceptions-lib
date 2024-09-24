package io.carlosrmfernandes.exceptions

import io.carlosrmfernandes.kotlinproblemdetailexceptions.enums.ExceptionsFieldsEnum
import io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions.ProblemDetailException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatusCode

class ProblemDetailExceptionTest {

    private val title = "Test Title"
    private val detail = "Detailed error message"
    private val userTitle = "User-friendly title"
    private val userMessage = "User-friendly message"
    private val httpStatus = HttpStatusCode.valueOf(400)
    private val internalCode = "ERR001"
    private val cause = Throwable("Cause message")

    private lateinit var exception: ProblemDetailException

    @BeforeEach
    fun setUp() {
        exception = object : ProblemDetailException(
            title, detail, userTitle, userMessage, httpStatus, internalCode, cause
        ) {}
    }

    @Test
    fun `test if exception initializes correctly`() {
        assertEquals(title, exception.title)
        assertEquals(detail, exception.detail)
        assertEquals(userTitle, exception.userTitle)
        assertEquals(userMessage, exception.userMessage)
        assertEquals(httpStatus, exception.httpStatus)
        assertEquals(internalCode, exception.internalCode)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun `test toMap method returns correct map`() {
        val map = exception.toMap()

        assertEquals(title, map[ExceptionsFieldsEnum.TITLE.value])
        assertEquals(httpStatus.value(), map[ExceptionsFieldsEnum.STATUS.value])
        assertEquals(detail, map[ExceptionsFieldsEnum.DETAIL.value])
        assertEquals(internalCode, map[ExceptionsFieldsEnum.INTERNAL_CODE.value])
        assertEquals(userMessage, map[ExceptionsFieldsEnum.USER_MESSAGE.value])
        assertEquals(userTitle, map[ExceptionsFieldsEnum.USER_TITLE.value])
        assertEquals(cause.message, map[ExceptionsFieldsEnum.PREVIOUS_MESSAGE.value])
        assertEquals(cause.javaClass.simpleName, map[ExceptionsFieldsEnum.PREVIOUS_TYPE.value])
        assertNotNull(map[ExceptionsFieldsEnum.LOCATION.value])
    }
}
