package com.alapierre.cli.infrastructure.ui.action

import com.alapierre.cli.domain.dto.CompleteHabitRequestDto
import com.alapierre.cli.domain.usecase.CompleteHabitUseCase
import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.exception.UIErrorHandler

class CompleteHabitAction(
    private val completeHabitUseCase: CompleteHabitUseCase,
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
                    promptMessageKey = "complete.prompt",
                    errorMessageKey = "select.error.invalid_habit_number"
                ) ?: return
                completeHabitUseCase.execute(CompleteHabitRequestDto(habitId))
                console.printSuccess(key = "complete.success")
            }
        })
    }

}