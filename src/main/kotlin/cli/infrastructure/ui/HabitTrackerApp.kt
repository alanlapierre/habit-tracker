package com.alapierre.cli.infrastructure.ui

import com.alapierre.cli.infrastructure.utils.Messages
import cli.domain.exception.HabitServiceException
import cli.domain.service.HabitService
import com.alapierre.cli.domain.dto.AddHabitRequestDto
import com.alapierre.cli.domain.dto.CompleteHabitRequestDto
import com.alapierre.cli.domain.dto.DeleteHabitRequestDto
import com.alapierre.cli.infrastructure.ui.common.ConsoleManager
import com.alapierre.cli.infrastructure.ui.menu.Menu

class HabitTrackerApp(
    private val habitService: HabitService,
    private val messages: Messages,
    private val menu: Menu,
    private val console: ConsoleManager
) {

    fun run() {
        menu.registerOption(1, messages.get("menu.option.add")) { addHabit() }
        menu.registerOption(2, messages.get("menu.option.list")) { listHabits() }
        menu.registerOption(3, messages.get("menu.option.complete")) { completeHabit() }
        menu.registerOption(4, messages.get("menu.option.delete")) { deleteHabit() }
        menu.registerOption(5, messages.get("menu.option.stats")) { showHabitStats() }
        menu.registerOption(6, messages.get("menu.option.exit")) { exitApp() }

        menu.showAndHandleOptions()
    }

    private fun addHabit() {
        handleErrors({
            console.printColorPrompt("${messages.get("add.name.prompt")} ", false)
            val habitName = console.readInput()?.trim().orEmpty()

            console.printColorPrompt("${messages.get("add.frequency.prompt")} ", false)
            val habitFrequency = console.readInput()?.toIntOrNull()

            habitService.add(AddHabitRequestDto(habitName, habitFrequency))
            console.printSuccess(messages.get("add.success"))
        })
    }

    private fun listHabits(): List<Pair<Int, String>> {
        val habits = habitService.list()
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
        handleErrors({
            val habitId = selectHabit(
                messages.get("complete.prompt"),
                messages.get("select.error.invalid_habit_number")
            ) ?: return
            habitService.complete(CompleteHabitRequestDto(habitId))
            console.printSuccess(messages.get("complete.success"))
        })
    }

    private fun deleteHabit() {
        handleErrors({
            val habitId = selectHabit(
                messages.get("delete.prompt"),
                messages.get("select.error.invalid_habit_number")
            ) ?: return
            habitService.delete(DeleteHabitRequestDto(habitId))
            console.printSuccess(messages.get("delete.success"))
        })
    }


    private fun showHabitStats() {
        handleErrors({
            val stats = habitService.getStatistics()
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

    private inline fun handleErrors(action: () -> Unit, defaultErrorMessage: String = messages.get("error.unknown")) {
        try {
            action()
        } catch (e: HabitServiceException) {
            console.printError(e.message ?: defaultErrorMessage)
        } catch (e: IllegalArgumentException) {
            console.printError(e.message ?: defaultErrorMessage)
        } catch (e: Exception) {
            console.printError(defaultErrorMessage)
        }
    }
}