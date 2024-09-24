import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.carlosrmfernandes.kotlinproblemdetailexceptions.enums.ExceptionsFieldsEnum
import io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions.UnexpectedException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class UnexpectedExceptionTest {

    private lateinit var cause: Throwable
    private lateinit var unexpectedException: UnexpectedException

    @BeforeEach
    fun setUp() {
        cause = Throwable("Test unexpected error")
        unexpectedException = UnexpectedException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            cause = cause
        )
    }

    @Test
    fun `test UnexpectedException initializes with default values`() {
        assertEquals("Test unexpected error", unexpectedException.title)
        assertTrue(unexpectedException.detail.contains("exception_code"))
        assertEquals("Ocorreu um erro inesperado", unexpectedException.userTitle)
        assertEquals("Entre em contato com o administrador do sistema", unexpectedException.userMessage)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, unexpectedException.httpStatus)
        assertEquals("UNEXPECTED_ERROR", unexpectedException.internalCode)
        assertEquals(cause, unexpectedException.cause)
    }

    @Test
    fun `test UnexpectedException initializes with custom HttpStatus`() {
        val customHttpStatus = HttpStatus.NOT_FOUND
        unexpectedException = UnexpectedException(httpStatus = customHttpStatus, cause = cause)

        assertEquals(HttpStatus.NOT_FOUND, unexpectedException.httpStatus)
    }

    @Test
    fun `test createDetail method returns correct JSON format`() {
        val detail = unexpectedException.detail
        val detailMap = jacksonObjectMapper().readValue(detail, Map::class.java)

        assertNotNull(detailMap["exception_code"])
        assertNotNull(detailMap["exception_line"])
        assertNotNull(detailMap["exception_message"])
        assertNotNull(detailMap["exception_file"])
    }

    @Test
    fun `test toMap with cause returns correct values`() {
        val map = unexpectedException.toMap()

        assertEquals(cause.message, map[ExceptionsFieldsEnum.PREVIOUS_MESSAGE.value])
        assertEquals(cause.javaClass.simpleName, map[ExceptionsFieldsEnum.PREVIOUS_TYPE.value])
    }
}
