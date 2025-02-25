package com.alapierre.cli.domain.exception


import cli.domain.exception.*
import cli.infrastructure.ui.utils.ConsoleManager

class HabitErrorHandler(val console: ConsoleManager) {

      inline fun <T> handle(block: () -> T): T {
        return try {
            block()
        } catch (e: HabitException) {
            when (e.errorCode) {
                "INVALID_NAME" -> throw HabitSaveException(key ="add.error.invalid_name")
                "INVALID_FREQUENCY" -> throw HabitSaveException(key = "add.error.invalid_frequency")
                "ALREADY_COMPLETED" -> throw HabitCompletionException(key = "complete.error.already_completed")
                "INVALID_ID" -> throw HabitFindException(key = "find.error.habit.not_found")
                else -> throw HabitServiceException(key = "error.unknown")
            }
        } catch (e: Exception) {
            throw HabitServiceException(key = "error.unknown")
        }
    }
}