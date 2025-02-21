package com.alapierre.cli.domain.mapper

import cli.domain.model.Habit
import cli.domain.model.HabitStatistics
import com.alapierre.cli.domain.dto.HabitResponseDto
import com.alapierre.cli.domain.dto.HabitStatisticsResponseDto

fun Habit.toDto(): HabitResponseDto {
    return HabitResponseDto(
        id = this.id,
        name = this.name,
        frequencyPerWeek = this.frequencyPerWeek,
        completions = this.completions
    )
}

fun HabitStatistics.toDto(): HabitStatisticsResponseDto {
    return HabitStatisticsResponseDto(
        totalHabits = this.totalHabits,
        completedThisWeek = this.completedThisWeek,
        percentageCompletion = this.percentageCompletion,
        bestHabit = this.bestHabit,
        worstHabit = this.worstHabit
    )
}