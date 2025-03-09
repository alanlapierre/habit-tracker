package com.alapierre.cli.infrastructure.ui.action.impl

import com.alapierre.cli.domain.usecase.ListHabitsUseCase
import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.action.HabitAction
import com.alapierre.cli.infrastructure.ui.action.ListResult

class ListHabitsAction(
    private val listHabitsUseCase: ListHabitsUseCase,
    private val console: ConsoleManager
): HabitAction {

    override fun execute(): ListResult {
        val habits = listHabitsUseCase.execute()
        if (habits.isEmpty()) {
            console.printWarning(key = "list.empty")
            return ListResult(habits)
        }
        console.printInfo(key = "list.header")
        habits.forEachIndexed { index, habit ->
            console.printPrompt(text = "${index + 1}. ${habit.name} - Completado: ${habit.completions.size}/${habit.frequencyPerWeek}")
        }

        return ListResult(habits)
    }

}