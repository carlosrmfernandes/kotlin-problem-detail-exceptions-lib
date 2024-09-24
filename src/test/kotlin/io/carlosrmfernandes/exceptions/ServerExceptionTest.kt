import io.carlosrmfernandes.kotlinproblemdetailexceptions.enums.ExceptionsFieldsEnum
import io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions.ServerException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ServerExceptionTest {

    private lateinit var serverException: ServerException

    @BeforeEach
    fun setUp() {
        serverException = ServerException()
    }

    @Test
    fun `test ServerException initializes with default values`() {
        assertEquals("Erro interno do servidor", serverException.title)
        assertEquals(
            "O servidor encontrou um erro interno e não foi capaz de completar sua requisição",
            serverException.detail
        )
        assertEquals("Erro interno do servidor", serverException.userTitle)
        assertEquals(
            "O servidor encontrou um erro interno e não foi capaz de completar sua requisição.",
            serverException.userMessage
        )
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serverException.httpStatus)
        assertEquals("UNEX0001", serverException.internalCode)
        assertNull(serverException.cause)
    }

    @Test
    fun `test ServerException initializes with custom HttpStatus and cause`() {
        val customHttpStatus = HttpStatus.SERVICE_UNAVAILABLE
        val cause = Throwable("Test cause")

        serverException = ServerException(httpStatus = customHttpStatus, cause = cause)

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, serverException.httpStatus)
        assertEquals(cause, serverException.cause)
    }

    @Test
    fun `test toMap method returns correct values`() {
        val map = serverException.toMap()

        assertEquals(serverException::class.simpleName, map[ExceptionsFieldsEnum.TYPE.value])
        assertEquals(serverException.title, map[ExceptionsFieldsEnum.TITLE.value])
        assertEquals(serverException.httpStatus.value(), map[ExceptionsFieldsEnum.STATUS.value])
        assertEquals(serverException.detail, map[ExceptionsFieldsEnum.DETAIL.value])
        assertEquals(serverException.internalCode, map[ExceptionsFieldsEnum.INTERNAL_CODE.value])
        assertEquals(serverException.userMessage, map[ExceptionsFieldsEnum.USER_MESSAGE.value])
        assertEquals(serverException.userTitle, map[ExceptionsFieldsEnum.USER_TITLE.value])
        assertNull(map[ExceptionsFieldsEnum.PREVIOUS_MESSAGE.value])
        assertNull(map[ExceptionsFieldsEnum.PREVIOUS_TYPE.value])
    }

    @Test
    fun `test toMap with cause returns correct values`() {
        val cause = Throwable("Test cause")
        serverException = ServerException(cause = cause)
        val map = serverException.toMap()

        assertEquals(cause.message, map[ExceptionsFieldsEnum.PREVIOUS_MESSAGE.value])
        assertEquals(cause.javaClass.simpleName, map[ExceptionsFieldsEnum.PREVIOUS_TYPE.value])
    }
}
