package com.alapierre.cli.infrastructure.ui.exception


import cli.domain.exception.*
import com.alapierre.cli.infrastructure.ui.common.ConsoleManager
import com.alapierre.cli.infrastructure.utils.Messages

class UIErrorHandler(val messages: Messages,
                     val console: ConsoleManager) {

    inline fun handle(action: () -> Unit, defaultErrorMessage: String = messages.get("error.unknown")) {
        try {
            action()
        } catch (e: HabitServiceException) {
            console.printError(e.message ?: defaultErrorMessage)
        } catch (e: IllegalArgumentException) {
            console.printError(e.message ?: defaultErrorMessage)
        } catch (e: Exception) {
            console.printError(defaultErrorMessage)
        }
    }
}