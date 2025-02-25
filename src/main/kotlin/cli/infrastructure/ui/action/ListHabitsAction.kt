package com.alapierre.cli.infrastructure.ui.action

import com.alapierre.cli.domain.dto.HabitResponseDto
import com.alapierre.cli.domain.usecase.ListHabitsUseCase
import cli.infrastructure.ui.utils.ConsoleManager

class ListHabitsAction(
    private val listHabitsUseCase: ListHabitsUseCase,
    private val console: ConsoleManager
) {

    fun execute(): List<HabitResponseDto> {
        val habits = listHabitsUseCase.execute()
        if (habits.isEmpty()) {
            console.printWarning(key = "list.empty")
            return habits
        }
        console.printInfo(key = "list.header")
        habits.forEachIndexed { index, habit ->
            console.printPrompt(text = "${index + 1}. ${habit.name} - Completado: ${habit.completions.size}/${habit.frequencyPerWeek}")
        }

        return habits
    }
}