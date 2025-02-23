package com.alapierre.cli.infrastructure.ui

import com.alapierre.cli.infrastructure.utils.Messages
import com.alapierre.cli.domain.dto.AddHabitRequestDto
import com.alapierre.cli.domain.dto.CompleteHabitRequestDto
import com.alapierre.cli.domain.dto.DeleteHabitRequestDto
import com.alapierre.cli.domain.usecase.*
import com.alapierre.cli.infrastructure.ui.common.ConsoleManager
import com.alapierre.cli.infrastructure.ui.exception.UIErrorHandler
import com.alapierre.cli.infrastructure.ui.menu.Menu


class HabitTrackerApp(
    private val addHabitUseCase: AddHabitUseCase,
    private val calculateHabitStatisticsUseCase: CalculateHabitStatisticsUseCase,
    private val completeHabitUseCase: CompleteHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val listHabitsUseCase: ListHabitsUseCase,
    private val messages: Messages,
    private val menu: Menu,
    private val console: ConsoleManager,
    private val errorHandler: UIErrorHandler = UIErrorHandler(messages, console)
) {

    fun run() {
        menu.start(mapOf(
            1 to { addHabit() },
            2 to { listHabits() },
            3 to { completeHabit() },
            4 to { deleteHabit() },
            5 to { showHabitStats() },
            6 to { exitApp() }
        ))
    }

    private fun addHabit() {
        errorHandler.handle({
            console.printColorPrompt("${messages.get("add.name.prompt")} ", false)
            val habitName = console.readInput()?.trim().orEmpty()

            console.printColorPrompt("${messages.get("add.frequency.prompt")} ", false)
            val habitFrequency = console.readInput()?.toIntOrNull()

            addHabitUseCase.execute(AddHabitRequestDto(habitName, habitFrequency))
            console.printSuccess(messages.get("add.success"))
        })
    }

    private fun listHabits(): List<Pair<Int, String>> {
        val habits = listHabitsUseCase.execute()
        if (habits.isEmpty()) {
            console.printWarning(messages.get("list.empty"))
            return emptyList()
        }
        console.printInfo(messages.get("list.header"))
        habits.forEachIndexed { index, habit ->
            console.printPrompt("${index + 1}. ${habit.name} - Completado: ${habit.completions.size}/${habit.frequencyPerWeek}")
        }
        return habits.mapIndexed { index, habit -> index + 1 to habit.id }
    }

    private fun completeHabit() {
        errorHandler.handle({
            val habitId = selectHabit(
                messages.get("complete.prompt"),
                messages.get("select.error.invalid_habit_number")
            ) ?: return
            completeHabitUseCase.execute(CompleteHabitRequestDto(habitId))
            console.printSuccess(messages.get("complete.success"))
        })
    }

    private fun deleteHabit() {
        errorHandler.handle({
            val habitId = selectHabit(
                messages.get("delete.prompt"),
                messages.get("select.error.invalid_habit_number")
            ) ?: return
            deleteHabitUseCase.execute(DeleteHabitRequestDto(habitId))
            console.printSuccess(messages.get("delete.success"))
        })
    }

    private fun showHabitStats() {
        errorHandler.handle({
            val stats = calculateHabitStatisticsUseCase.execute()
            console.printInfo(messages.get("stats.title"))
            console.printPrompt("${messages.get("stats.total")} ${stats.totalHabits}")
            console.printPrompt("${messages.get("stats.completed_this_week")} ${stats.completedThisWeek}")
            console.printPrompt("${messages.get("stats.global_progress")} ${stats.percentageCompletion}%")
            console.printPrompt("${messages.get("stats.best_habit")} ${stats.bestHabit}")
            console.printPrompt("${messages.get("stats.worst_habit")} ${stats.worstHabit}")
        } , messages.get("stats.error.retrieve"))
    }

    private fun selectHabit(promptMessage: String, errorMessage: String): String? {
        val habitIndexToIdMap = listHabits()
        if (habitIndexToIdMap.isEmpty()) return null

        console.printColorPrompt("$promptMessage ", false)
        val habitNumber = console.readInput()?.toIntOrNull()

        return habitIndexToIdMap.find { it.first == habitNumber }?.second
            ?: throw IllegalArgumentException(errorMessage)
    }

    private fun exitApp() {
        console.printSuccess(messages.get("exit.message"))
        System.exit(0)
    }

}