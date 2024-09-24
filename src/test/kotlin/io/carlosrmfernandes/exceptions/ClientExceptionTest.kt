package io.carlosrmfernandes.exceptions

import io.carlosrmfernandes.kotlinproblemdetailexceptions.enums.ExceptionsFieldsEnum
import io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions.ClientException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ClientExceptionTest {

    private lateinit var clientException: ClientException

    @BeforeEach
    fun setUp() {
        clientException = ClientException()
    }

    @Test
    fun `test ClientException initializes with default values`() {
        assertEquals("Erro de cliente na requisição", clientException.title)
        assertEquals(
            "O servidor não foi capaz de processar a requisição por causa de algo que é percebido como um erro do cliente",
            clientException.detail
        )
        assertEquals("Erro na requisição", clientException.userTitle)
        assertEquals(
            "Não foi possível processar a solicitação. Tente novamente. Se o problema persistir, entre em contato com o suporte.",
            clientException.userMessage
        )
        assertEquals(HttpStatus.BAD_REQUEST, clientException.httpStatus)
        assertEquals("CLIE0001", clientException.internalCode)
        assertNull(clientException.cause)
    }

    @Test
    fun `test ClientException initializes with custom HttpStatus and cause`() {
        val customHttpStatus = HttpStatus.UNAUTHORIZED
        val cause = Throwable("Test cause")

        clientException = ClientException(httpStatus = customHttpStatus, cause = cause)

        assertEquals(HttpStatus.UNAUTHORIZED, clientException.httpStatus)
        assertEquals(cause, clientException.cause)
    }

    @Test
    fun `test toMap method returns correct values`() {
        val map = clientException.toMap()

        assertEquals(clientException::class.simpleName, map[ExceptionsFieldsEnum.TYPE.value])
        assertEquals(clientException.title, map[ExceptionsFieldsEnum.TITLE.value])
        assertEquals(clientException.httpStatus.value(), map[ExceptionsFieldsEnum.STATUS.value])
        assertEquals(clientException.detail, map[ExceptionsFieldsEnum.DETAIL.value])
        assertEquals(clientException.internalCode, map[ExceptionsFieldsEnum.INTERNAL_CODE.value])
        assertEquals(clientException.userMessage, map[ExceptionsFieldsEnum.USER_MESSAGE.value])
        assertEquals(clientException.userTitle, map[ExceptionsFieldsEnum.USER_TITLE.value])
        assertNull(map[ExceptionsFieldsEnum.PREVIOUS_MESSAGE.value])
        assertNull(map[ExceptionsFieldsEnum.PREVIOUS_TYPE.value])
    }

    @Test
    fun `test toMap with cause returns correct values`() {
        val cause = Throwable("Test cause")
        clientException = ClientException(cause = cause)
        val map = clientException.toMap()

        assertEquals(cause.message, map[ExceptionsFieldsEnum.PREVIOUS_MESSAGE.value])
        assertEquals(cause.javaClass.simpleName, map[ExceptionsFieldsEnum.PREVIOUS_TYPE.value])
    }
}