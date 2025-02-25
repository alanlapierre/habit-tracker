package com.alapierre.cli.infrastructure.ui.action

import com.alapierre.cli.domain.usecase.CalculateHabitStatisticsUseCase
import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.exception.UIErrorHandler

class ShowHabitStatisticsAction(
    private val calculateHabitStatisticsUseCase: CalculateHabitStatisticsUseCase,
    private val console: ConsoleManager,
    private val errorHandler: UIErrorHandler
) {

    fun execute() {
        errorHandler.handle({
            val stats = calculateHabitStatisticsUseCase.execute()
            console.printInfo(key = "stats.title")

            console.printPrompt(key = "stats.total", newLine = false)
            console.printPrompt(text = " ${stats.totalHabits}")

            console.printPrompt(key = "stats.completed_this_week", newLine = false)
            console.printPrompt(text = " ${stats.completedThisWeek}")

            console.printPrompt(key = "stats.global_progress", newLine = false)
            console.printPrompt(text = " ${stats.percentageCompletion}")

            console.printPrompt(key = "stats.best_habit", newLine = false)
            console.printPrompt(text = " ${stats.bestHabit}")

            console.printPrompt(key = "stats.worst_habit", newLine = false)
            console.printPrompt(text = " ${stats.worstHabit}")

        } , console.getMessageOrEmpty("stats.error.retrieve"))
    }

}