package com.alapierre.cli.domain.service

import cli.domain.model.Habit
import cli.domain.model.HabitStatistics
import cli.domain.utils.Clock
import java.time.LocalDate

class HabitStatisticsCalculator(private val clock: Clock) {

    fun calculate(habits: List<Habit>): HabitStatistics {
        val totalHabits = habits.size

        val thisWeek = clock.now().minusDays(clock.now().dayOfWeek.value.toLong())..clock.now()
        val completedThisWeek = habits.count { habit ->
            habit.completions.any { date -> LocalDate.parse(date) in thisWeek }
        }

        val totalProgress = habits.sumOf { habit ->
            habit.completions.size.toDouble() / habit.frequencyPerWeek
        }
        val percentageCompletion = if (habits.isNotEmpty()) (totalProgress / habits.size) * 100 else 0.0

        val bestHabit = habits.maxByOrNull { it.completions.size }
        val worstHabit = habits.minByOrNull { it.completions.size }

        return HabitStatistics(
            totalHabits = totalHabits,
            completedThisWeek = completedThisWeek,
            percentageCompletion = percentageCompletion,
            bestHabit = bestHabit?.name.orEmpty(),
            worstHabit = worstHabit?.name.orEmpty()
        )


    }
}