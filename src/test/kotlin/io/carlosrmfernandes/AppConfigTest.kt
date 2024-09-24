package io.carlosrmfernandes

import io.carlosrmfernandes.kotlinproblemdetailexceptions.config.AppConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

class AppConfigTest {

    @BeforeTest
    fun setup() {
        System.setProperty("PROBLEM_DETAIL_EXCEPTION_APP_NAME", "TestApp")
        System.setProperty("PROBLEM_DETAIL_EXCEPTION_GENERATE_LOGS", "false")
    }


    @Test
    fun `test appName defaults to APP when environment variable is not set`() {
        System.clearProperty("PROBLEM_DETAIL_EXCEPTION_APP_NAME")
        val defaultAppName = AppConfig.appName
        assertEquals("APP", defaultAppName)
    }


    @Test
    fun `test logThrow defaults to true when environment variable is not set`() {
        System.clearProperty("PROBLEM_DETAIL_EXCEPTION_GENERATE_LOGS")
        val defaultLogThrow = AppConfig.logThrow
        assertTrue(defaultLogThrow)
    }
}