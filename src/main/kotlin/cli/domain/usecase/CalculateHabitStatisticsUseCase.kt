package com.alapierre.cli.domain.usecase

import cli.domain.repository.HabitRepository
import com.alapierre.cli.domain.dto.HabitStatisticsResponseDto
import com.alapierre.cli.domain.mapper.toDto
import com.alapierre.cli.domain.service.HabitStatisticsCalculator

class CalculateHabitStatisticsUseCase(
    private val repository: HabitRepository,
    private val statisticsCalculator: HabitStatisticsCalculator) {

    fun execute(): HabitStatisticsResponseDto {
        val habits = repository.list()
        val statistics = statisticsCalculator.calculate(habits)
        return statistics.toDto()
    }

}