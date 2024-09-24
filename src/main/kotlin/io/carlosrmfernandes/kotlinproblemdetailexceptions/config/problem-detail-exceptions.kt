package io.carlosrmfernandes.kotlinproblemdetailexceptions.config

object AppConfig {
    val appName: String = System.getenv("PROBLEM_DETAIL_EXCEPTION_APP_NAME") ?: "APP"
    val logThrow: Boolean = System.getenv("PROBLEM_DETAIL_EXCEPTION_GENERATE_LOGS")?.toBoolean() ?: true
}
