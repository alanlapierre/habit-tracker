package cli.domain.repository

import cli.domain.model.Habit

interface HabitRepository {
    fun add(habit: Habit)
    fun list(): List<Habit>
    fun delete(habitId: String)
    fun findById(id: String): Habit?
    fun addCompletion(habitId: String)
}