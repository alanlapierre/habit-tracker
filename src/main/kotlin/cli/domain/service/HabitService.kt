package cli.domain.service

import cli.domain.exception.*
import cli.domain.utils.Clock
import cli.domain.model.Habit
import cli.domain.repository.HabitRepository
import com.alapierre.cli.domain.dto.*
import com.alapierre.cli.domain.exception.HabitErrorHandler
import com.alapierre.cli.domain.mapper.toDto
import com.alapierre.cli.domain.service.HabitStatisticsCalculator

class HabitService(private val repository: HabitRepository,
                   private val clock: Clock,
                   private val statisticsCalculator: HabitStatisticsCalculator,
                   private val errorHandler: HabitErrorHandler
) {

    fun add(request: AddHabitRequestDto) {
        errorHandler.handle {
            val habit = Habit(
                name = request.name,
                frequencyPerWeek = request.frequencyPerWeek ?: 0)
            repository.add(habit)
        }
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

        errorHandler.handle {
            habit.addCompletion(clock.now())
            repository.addCompletion(request.id)
        }
    }

    fun getStatistics(): HabitStatisticsResponseDto {
        val habits = repository.list()
        val statistics = statisticsCalculator.calculate(habits)
        return statistics.toDto()
    }

    private fun findById(id: String): Habit {

        errorHandler.handle {
           Habit.validateId(id)
           return repository.findById(id)
                ?: throw HabitException(errorCode = "INVALID_ID")
        }
    }

}