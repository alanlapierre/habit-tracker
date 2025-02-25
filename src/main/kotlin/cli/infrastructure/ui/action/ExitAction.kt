package com.alapierre.cli.infrastructure.ui.action

import cli.infrastructure.ui.utils.ConsoleManager

class ExitAction(
    private val console: ConsoleManager
) {

    fun execute() {
        console.printSuccess(key = "exit.message")
        System.exit(0)
    }

}