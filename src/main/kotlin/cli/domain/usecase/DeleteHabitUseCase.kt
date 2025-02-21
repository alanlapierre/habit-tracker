package com.alapierre.cli.domain.usecase

import cli.domain.repository.HabitRepository
import cli.domain.service.HabitFinder
import com.alapierre.cli.domain.dto.DeleteHabitRequestDto
import com.alapierre.cli.domain.exception.HabitErrorHandler

class DeleteHabitUseCase(
    private val repository: HabitRepository,
    private val finder: HabitFinder,
    private val errorHandler: HabitErrorHandler) {

    fun execute(request: DeleteHabitRequestDto) {
        errorHandler.handle {
            finder.findById(request.id)
            repository.delete(request.id)
        }
    }

}