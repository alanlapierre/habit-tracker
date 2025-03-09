package com.alapierre.cli.infrastructure.ui.action.impl

import com.alapierre.cli.domain.dto.DeleteHabitRequestDto
import com.alapierre.cli.domain.usecase.DeleteHabitUseCase
import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.action.*
import com.alapierre.cli.infrastructure.ui.exception.UIErrorHandler


class DeleteHabitAction(
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val listHabitsAction: HabitAction,
    private val habitSelector: HabitSelector,
    private val console: ConsoleManager,
    private val errorHandler: UIErrorHandler
): HabitAction {

    override fun execute(): ActionResult {
        errorHandler.handle({
            val result = listHabitsAction.execute()

            if(result is ListResult) {
                val habits = result.habits
                if(habits.isNotEmpty()) {
                    val habitId = habitSelector.execute(
                        habits = habits,
                        promptMessageKey = "delete.prompt",
                        errorMessageKey = "select.error.invalid_habit_number"
                    ) ?: return NoResult
                    deleteHabitUseCase.execute(DeleteHabitRequestDto(habitId))
                    console.printSuccess(key = "delete.success")
                }
            }
        })

        return NoResult
    }

}