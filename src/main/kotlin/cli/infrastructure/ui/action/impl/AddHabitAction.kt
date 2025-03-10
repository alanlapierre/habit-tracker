package com.alapierre.cli.infrastructure.ui.action.impl

import com.alapierre.cli.domain.dto.AddHabitRequestDto
import com.alapierre.cli.domain.usecase.AddHabitUseCase
import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.action.HabitAction
import com.alapierre.cli.infrastructure.ui.action.ActionResult
import com.alapierre.cli.infrastructure.ui.action.NoResult
import com.alapierre.cli.infrastructure.ui.exception.UIErrorHandler

class AddHabitAction(
    private val addHabitUseCase: AddHabitUseCase,
    private val console: ConsoleManager,
    private val errorHandler: UIErrorHandler
): HabitAction {
    override fun execute(): ActionResult {
        errorHandler.handle({
            console.printColorPrompt(key = "add.name.prompt", newLine = false)
            console.printColorPrompt(text = " ", newLine = false)
            val habitName = console.readInput()?.trim().orEmpty()

            console.printColorPrompt(key = "add.frequency.prompt", newLine = false)
            console.printColorPrompt(text = " ", newLine = false)
            val habitFrequency = console.readInput()?.toIntOrNull()

            addHabitUseCase.execute(AddHabitRequestDto(habitName, habitFrequency))
            console.printSuccess(key = "add.success")
        })

        return NoResult
    }

}