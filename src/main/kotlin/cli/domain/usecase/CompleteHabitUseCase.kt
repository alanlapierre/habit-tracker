package com.alapierre.cli.domain.usecase

import cli.domain.repository.HabitRepository
import cli.domain.service.HabitFinder
import cli.domain.utils.Clock
import com.alapierre.cli.domain.dto.CompleteHabitRequestDto
import com.alapierre.cli.domain.exception.HabitErrorHandler

class CompleteHabitUseCase(
    private val repository: HabitRepository,
    private val errorHandler: HabitErrorHandler,
    private val finder: HabitFinder,
    private val clock: Clock) {

    fun execute(request: CompleteHabitRequestDto) {

        errorHandler.handle {
            val habit = finder.findById(request.id)
            habit.addCompletion(clock.now())
            repository.addCompletion(request.id)
        }
    }

}