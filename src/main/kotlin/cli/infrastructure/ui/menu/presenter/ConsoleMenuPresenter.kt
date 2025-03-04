package com.alapierre.cli.infrastructure.ui.menu.presenter

import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.menu.MenuOption


class ConsoleMenuPresenter(private val console: ConsoleManager): MenuPresenter {
    override fun displayMenu(options: Map<Int, MenuOption>) {
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
}