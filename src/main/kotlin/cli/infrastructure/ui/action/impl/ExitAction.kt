package com.alapierre.cli.infrastructure.ui.action.impl

import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.action.HabitAction
import com.alapierre.cli.infrastructure.ui.action.ActionResult
import com.alapierre.cli.infrastructure.ui.action.NoResult

class ExitAction(private val console: ConsoleManager): HabitAction {

    override fun execute(): ActionResult {
        console.printSuccess(key = "exit.message")
        System.exit(0)

        return NoResult
    }

}