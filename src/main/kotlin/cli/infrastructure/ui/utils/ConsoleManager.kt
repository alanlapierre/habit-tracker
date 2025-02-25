package cli.infrastructure.ui.utils

import com.alapierre.cli.infrastructure.utils.Messages

class ConsoleManager(
    private val messages: Messages
) {

    companion object {
        const val BOLD = "\u001b[1m"
        const val CYAN = "\u001b[36m"
        const val GREEN = "\u001b[32m"
        const val RED = "\u001b[31m"
        const val RESET = "\u001b[0m"
        const val YELLOW = "\u001b[33m"
    }

    fun printColorPrompt(text: String? = null, key: String? = null, newLine: Boolean = true) {
        val formattedMessage = "$BOLD$CYAN${text ?: getMessageOrEmpty(key)}$RESET"
        shouldPrint(newLine, formattedMessage)
    }

    fun printPrompt(text: String? = null, key: String? = null, newLine: Boolean = true) {
        val formattedMessage = "$BOLD${text ?: getMessageOrEmpty(key)}$RESET"
        shouldPrint(newLine, formattedMessage)
    }

    fun printSuccess(key: String, newLine: Boolean = true) {
        val formattedMessage = "$GREEN${getMessageOrEmpty(key)}$RESET"
        shouldPrint(newLine, formattedMessage)
    }

    fun printError(key: String, newLine: Boolean = true) {
        val formattedMessage = "$RED${getMessageOrEmpty(key)}$RESET"
        shouldPrint(newLine, formattedMessage)
    }

    fun printWarning(key: String, newLine: Boolean = true) {
        val formattedMessage = "$YELLOW${getMessageOrEmpty(key)}$RESET"
        shouldPrint(newLine, formattedMessage)
    }

    fun printInfo(key: String, newLine: Boolean = true) {
        val formattedMessage = "$CYAN${getMessageOrEmpty(key)}$RESET"
        shouldPrint(newLine, formattedMessage)
    }

    fun readInput(): String? {
        return readlnOrNull()
    }

    fun getMessageOrEmpty(key: String?): String {
        return key?.let { messages.get(it) } ?: ""
    }

    private fun shouldPrint(newLine: Boolean, formattedMessage: String) {
        if (formattedMessage.isEmpty()) return
        if (newLine) println(formattedMessage) else print(formattedMessage)
    }

}