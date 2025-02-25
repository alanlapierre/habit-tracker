package com.alapierre.cli.infrastructure.ui.menu

import cli.infrastructure.ui.utils.ConsoleManager


class Menu(private val console: ConsoleManager
) {

    private val options = mutableMapOf<Int, MenuOption>()

    fun start(actions: Map<Int, () -> Unit>) {
        configure(actions)
        showAndHandleOptions()
    }

    private fun configure(actions: Map<Int, () -> Unit>) {

        actions.forEach { (optionId, action) ->
            val descriptionKey = "menu.option.${optionId}"
            options[optionId] = MenuOption(optionId, descriptionKey, action)
        }

    }

    private fun showAndHandleOptions() {
        while (true) {
            displayMenu()
            val selectedOption = readUserOption()
            options[selectedOption]?.action?.invoke() ?: console.printError(key = "menu.error.invalid_option")
        }
    }

    private fun displayMenu() {
        console.printColorPrompt(text = "═══════════════════════════════════════════")
        console.printColorPrompt(key = "menu.title")
        console.printColorPrompt(text = "═══════════════════════════════════════════")
        options.values.forEach {
            console.printPrompt(text = "${it.id}. ", newLine = false)
            console.printPrompt(key = it.descriptionKey)
        }
        console.printColorPrompt(key = "menu.select_option", newLine = false)
        console.printColorPrompt(text = " ", newLine = false)
    }

    private fun readUserOption() = readlnOrNull()?.toIntOrNull()
}