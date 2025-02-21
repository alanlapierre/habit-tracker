package com.alapierre.cli.domain.dto

data class AddHabitRequestDto(
    val name: String,
    val frequencyPerWeek: Int?
)

data class CompleteHabitRequestDto(
    val id: String
)

data class DeleteHabitRequestDto(
    val id: String
)

data class HabitResponseDto(
    val id: String,
    val name: String,
    val frequencyPerWeek: Int,
    val completions: List<String>
)

data class HabitStatisticsResponseDto(
    val totalHabits: Int,
    val completedThisWeek: Int,
    val percentageCompletion: Double,
    val bestHabit: String,
    val worstHabit: String
)