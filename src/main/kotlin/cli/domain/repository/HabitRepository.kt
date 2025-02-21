package com.alapierre.cli.application.repository

import com.alapierre.cli.application.model.Habit

interface HabitRepository {
    fun add(habit: Habit)
    fun list(): List<Habit>
    fun delete(habitId: String)
    fun findById(id: String): Habit?
    fun addCompletion(habitId: String)
}