package com.alapierre.cli.infrastructure.ui.exception


import cli.domain.exception.*
import cli.infrastructure.ui.utils.ConsoleManager

class UIErrorHandler(
    val console: ConsoleManager
) {

    inline fun handle(action: () -> Unit, defaultErrorMessage: String = console.getMessageOrEmpty(key = "error.unknown")) {
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