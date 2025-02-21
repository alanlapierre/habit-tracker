package com.alapierre.cli.application.service

import com.alapierre.cli.application.exception.HabitCompletionException
import com.alapierre.cli.application.exception.HabitNotFoundException
import com.alapierre.cli.application.exception.HabitSaveException
import com.alapierre.cli.application.exception.HabitServiceException
import com.alapierre.cli.application.util.Clock
import com.alapierre.cli.infrastructure.utils.Messages
import com.alapierre.cli.application.model.Habit
import com.alapierre.cli.application.model.HabitStatistics
import com.alapierre.cli.application.repository.HabitRepository
import com.alapierre.cli.domain.dto.*
import com.alapierre.cli.domain.mapper.toDto
import org.bson.types.ObjectId
import java.time.LocalDate

class HabitService(private val repository: HabitRepository,
                   private val messages: Messages,
                   private val clock: Clock
) {

    fun add(request: AddHabitRequestDto) {
        if (request.name.isNullOrEmpty() || request.name.isBlank())
            throw HabitSaveException(messages.get("add.error.invalid_name"))

        if (request.frequencyPerWeek == null || request.frequencyPerWeek <= 0)
            throw HabitServiceException(messages.get("add.error.invalid_frequency"))

        val habit = Habit(
            name = request.name,
            frequencyPerWeek = request.frequencyPerWeek!!)

        repository.add(habit)
    }

    fun list(): List<HabitResponseDto> {
        return repository.list().map {it.toDto()}
    }

    fun delete(request: DeleteHabitRequestDto) {
        findById(request.id)
        repository.delete(request.id)
    }

    fun complete(request: CompleteHabitRequestDto) {
        val habit = findById(request.id)
        val today = clock.now().toString()
        if (habit.completions.contains(today))
            throw HabitCompletionException(messages.get("complete.error.already_completed"))

        repository.addCompletion(request.id)
    }

    fun getStatistics(): HabitStatisticsResponseDto {
        val habits = repository.list()
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

        val statistics = HabitStatistics(
            totalHabits = totalHabits,
            completedThisWeek = completedThisWeek,
            percentageCompletion = percentageCompletion,
            bestHabit = bestHabit?.name.orEmpty(),
            worstHabit = worstHabit?.name.orEmpty()
        )

        return statistics.toDto()

    }

    private fun findById(id: String): Habit {
        if(!ObjectId.isValid(id))
            throw HabitNotFoundException(messages.get("error.habit.not_found"))

        return repository.findById(id)
            ?: throw HabitNotFoundException(messages.get("error.habit.not_found"))
    }

}