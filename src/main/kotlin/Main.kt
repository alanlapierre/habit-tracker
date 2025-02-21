package com.alapierre


import com.alapierre.cli.infrastructure.utils.Messages
import com.alapierre.cli.infrastructure.persistence.repository.HabitRepositoryImpl
import cli.domain.service.HabitFinder
import cli.domain.utils.Clock
import com.alapierre.cli.domain.exception.HabitErrorHandler
import com.alapierre.cli.domain.service.HabitStatisticsCalculator
import com.alapierre.cli.domain.usecase.*
import com.alapierre.cli.infrastructure.ui.HabitTrackerApp
import com.alapierre.cli.infrastructure.ui.common.ConsoleManager
import com.alapierre.cli.infrastructure.ui.menu.Menu

fun main() {

    val clock = Clock()
    val messages = Messages()
    val habitRepository = HabitRepositoryImpl()
    val console = ConsoleManager()

    val errorHabitHandler = HabitErrorHandler(messages)
    val statisticsCalculator = HabitStatisticsCalculator(clock)
    val habitFinder = HabitFinder(habitRepository)

    val menu = Menu(messages, console)

    val addHabitUseCase = AddHabitUseCase(habitRepository, errorHabitHandler)
    val calculateHabitStatisticsUseCase = CalculateHabitStatisticsUseCase(habitRepository, statisticsCalculator)
    val completeHabitUseCase = CompleteHabitUseCase(habitRepository, errorHabitHandler, habitFinder, clock)
    val deleteHabitUseCase = DeleteHabitUseCase(habitRepository, habitFinder, errorHabitHandler)
    val listHabitsUseCase = ListHabitsUseCase(habitRepository)


    HabitTrackerApp(
        addHabitUseCase,
        calculateHabitStatisticsUseCase,
        completeHabitUseCase,
        deleteHabitUseCase,
        listHabitsUseCase,
        messages,
        menu,
        console).run()
}

