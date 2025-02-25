package com.alapierre.cli.infrastructure.ui.action

import com.alapierre.cli.domain.dto.HabitResponseDto
import cli.infrastructure.ui.utils.ConsoleManager

class HabitSelector(
    private val console: ConsoleManager
) {
    fun execute(habits:  List<HabitResponseDto>, promptMessageKey: String, errorMessageKey: String): String? {
        val habitIndexToIdMap = habits.mapIndexed { index, habit -> index + 1 to habit.id }
        if (habitIndexToIdMap.isEmpty()) {
            console.printWarning(key = "list.empty")
            return null
        }

        console.printColorPrompt(key = promptMessageKey, newLine = false)
        console.printColorPrompt(text = " ", newLine = false)

        val habitNumber = console.readInput()?.toIntOrNull()

        return habitIndexToIdMap.find { it.first == habitNumber }?.second
            ?: throw IllegalArgumentException(errorMessageKey)
    }

}