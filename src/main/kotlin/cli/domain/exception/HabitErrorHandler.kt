package com.alapierre.cli.domain.exception


import cli.domain.exception.*
import com.alapierre.cli.infrastructure.utils.Messages

class HabitErrorHandler(val messages: Messages) {

      inline fun <T> handle(block: () -> T): T {
        return try {
            block()
        } catch (e: HabitException) {
            when (e.errorCode) {
                "INVALID_NAME" -> throw HabitSaveException(messages.get("add.error.invalid_name"))
                "INVALID_FREQUENCY" -> throw HabitSaveException(messages.get("add.error.invalid_frequency"))
                "ALREADY_COMPLETED" -> throw HabitCompletionException(messages.get("complete.error.already_completed"))
                "INVALID_ID" -> throw HabitFindException(messages.get("find.error.habit.not_found"))
                else -> throw HabitServiceException(messages.get("error.unknown"))
            }
        } catch (e: Exception) {
            throw HabitServiceException(messages.get("error.unknown"))
        }
    }
}