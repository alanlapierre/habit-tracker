package com.alapierre.cli.infrastructure.ui.action

import com.alapierre.cli.domain.dto.DeleteHabitRequestDto
import com.alapierre.cli.domain.usecase.DeleteHabitUseCase
import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.exception.UIErrorHandler


class DeleteHabitAction(
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val listHabitsAction: ListHabitsAction,
    private val habitSelector: HabitSelector,
    private val console: ConsoleManager,
    private val errorHandler: UIErrorHandler
) {

    fun execute() {
        errorHandler.handle({
            val habits = listHabitsAction.execute()

            if(habits.isNotEmpty()) {
                val habitId = habitSelector.execute(
                    habits = habits,
                    promptMessageKey = "delete.prompt",
                    errorMessageKey = "select.error.invalid_habit_number"
                ) ?: return
                deleteHabitUseCase.execute(DeleteHabitRequestDto(habitId))
                console.printSuccess(key = "delete.success")
            }
        })
    }
}