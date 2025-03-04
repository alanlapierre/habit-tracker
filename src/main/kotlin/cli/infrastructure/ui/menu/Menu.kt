package com.alapierre.cli.infrastructure.ui.menu

import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.menu.presenter.ConsoleMenuPresenter


class Menu(
    private val console: ConsoleManager,
    private val menuPresenter: ConsoleMenuPresenter
) {

    private val options = mutableMapOf<Int, MenuOption>()

    fun start(actions: Map<Int, () -> Unit>) {
        configure(actions)
        showAndHandleOptions()
    }

    private fun configure(actions: Map<Int, () -> Unit>) {
        actions.forEach { (optionId, action) ->
            options[optionId] = MenuOption.create(optionId, action)
        }
    }

    private fun showAndHandleOptions() {
        while (true) {
            menuPresenter.displayMenu(options)
            val selectedOption = console.readInput()?.toIntOrNull()
            options[selectedOption]?.action?.invoke() ?: console.printError(key = "menu.error.invalid_option")
        }
    }

}