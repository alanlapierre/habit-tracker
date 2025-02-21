package com.alapierre.cli.domain.usecase

import cli.domain.repository.HabitRepository
import com.alapierre.cli.domain.dto.HabitResponseDto
import com.alapierre.cli.domain.mapper.toDto

class ListHabitsUseCase(private val repository: HabitRepository) {

    fun execute(): List<HabitResponseDto> {
        return repository.list().map {it.toDto()}
    }

}