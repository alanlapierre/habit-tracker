package com.alapierre.cli.infrastructure.ui.common

class ConsoleManager {

    companion object {
        const val BOLD = "\u001b[1m"
        const val CYAN = "\u001b[36m"
        const val GREEN = "\u001b[32m"
        const val RED = "\u001b[31m"
        const val RESET = "\u001b[0m"
        const val YELLOW = "\u001b[33m"
    }

    fun printColorPrompt(message: String, newLine: Boolean = true) {
        if (newLine) println("$BOLD$CYAN$message$RESET") else print("$BOLD$CYAN$message$RESET")
    }

    fun printPrompt(message: String, newLine: Boolean = true) {
        if (newLine) println("$BOLD$message$RESET") else print("$BOLD$message$RESET")
    }

    fun printSuccess(message: String, newLine: Boolean = true) {
        if (newLine) println("$GREEN$message$RESET") else print("$GREEN$message$RESET")
    }

    fun printError(message: String, newLine: Boolean = true) {
        if (newLine) println("$RED$message$RESET") else print("$RED$message$RESET")
    }

    fun printWarning(message: String, newLine: Boolean = true) {
        if (newLine) println("$YELLOW$message$RESET") else print("$YELLOW$message$RESET")
    }

    fun printInfo(message: String, newLine: Boolean = true) {
        if (newLine) println("$CYAN$message$RESET") else print("$CYAN$message$RESET")
    }

    fun readInput(): String? {
        return readlnOrNull()
    }

}