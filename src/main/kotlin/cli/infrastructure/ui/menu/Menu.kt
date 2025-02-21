package com.alapierre.cli.infrastructure.ui.menu

import com.alapierre.cli.infrastructure.utils.Messages
import com.alapierre.cli.infrastructure.ui.common.ConsoleManager


class Menu(private val messages: Messages, private val console: ConsoleManager) {
    private val options = mutableMapOf<Int, MenuOption>()

    fun registerOption(id: Int, description: String, action: () -> Unit) {
        options[id] = MenuOption(id, description, action)
    }

    fun showAndHandleOptions() {
        while (true) {
            displayMenu()
            val selectedOption = readUserOption()
            options[selectedOption]?.action?.invoke() ?: console.printError(messages.get("menu.error.invalid_option"))
        }
    }

    private fun displayMenu() {
        console.printColorPrompt("═══════════════════════════════════════════")
        console.printColorPrompt("        ${messages.get("menu.title")} ")
        console.printColorPrompt("═══════════════════════════════════════════")
        options.values.forEach { console.printPrompt("${it.id}. ${it.description}") }
        console.printColorPrompt("${messages.get("menu.select_option")} ", false)
    }

    private fun readUserOption() = readlnOrNull()?.toIntOrNull()
}