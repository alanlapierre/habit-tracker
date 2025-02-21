package com.alapierre.cli.domain.usecase

import cli.domain.model.Habit
import cli.domain.repository.HabitRepository
import com.alapierre.cli.domain.dto.AddHabitRequestDto
import com.alapierre.cli.domain.exception.HabitErrorHandler

class AddHabitUseCase(
    private val repository: HabitRepository,
    private val errorHandler: HabitErrorHandler) {

    fun execute(request: AddHabitRequestDto) {
        errorHandler.handle {
            val habit = Habit(
                name = request.name,
                frequencyPerWeek = request.frequencyPerWeek ?: 0)
            repository.add(habit)
        }
    }
}