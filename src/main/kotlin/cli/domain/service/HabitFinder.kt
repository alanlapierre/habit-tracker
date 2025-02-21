package cli.domain.service

import cli.domain.exception.*
import cli.domain.model.Habit
import cli.domain.repository.HabitRepository


class HabitFinder(private val repository: HabitRepository) {

    fun findById(id: String): Habit {
       Habit.validateId(id)
       return repository.findById(id) ?: throw HabitException(errorCode = "INVALID_ID")
    }

}