package com.alapierre


import com.alapierre.cli.infrastructure.utils.Messages
import com.alapierre.cli.infrastructure.persistence.repository.HabitRepositoryImpl
import cli.domain.service.HabitFinder
import cli.domain.utils.Clock
import com.alapierre.cli.domain.exception.HabitErrorHandler
import com.alapierre.cli.domain.service.HabitStatisticsCalculator
import com.alapierre.cli.domain.usecase.*
import com.alapierre.cli.infrastructure.ui.HabitTrackerController
import cli.infrastructure.ui.utils.ConsoleManager
import com.alapierre.cli.infrastructure.ui.action.impl.*
import com.alapierre.cli.infrastructure.ui.action.HabitSelector
import com.alapierre.cli.infrastructure.ui.exception.UIErrorHandler
import com.alapierre.cli.infrastructure.ui.menu.Menu
import com.alapierre.cli.infrastructure.ui.menu.presenter.ConsoleMenuPresenter

fun main() {

    val clock = Clock()
    val messages = Messages()
    val habitRepository = HabitRepositoryImpl()
    val console = ConsoleManager(messages)

    val errorHabitHandler = HabitErrorHandler(console)
    val statisticsCalculator = HabitStatisticsCalculator(clock)
    val habitFinder = HabitFinder(habitRepository)

    val presenter = ConsoleMenuPresenter(console)

    val menu = Menu(console, presenter)

    val addHabitUseCase = AddHabitUseCase(habitRepository, errorHabitHandler)
    val calculateHabitStatisticsUseCase = CalculateHabitStatisticsUseCase(habitRepository, statisticsCalculator)
    val completeHabitUseCase = CompleteHabitUseCase(habitRepository, errorHabitHandler, habitFinder, clock)
    val deleteHabitUseCase = DeleteHabitUseCase(habitRepository, habitFinder, errorHabitHandler)
    val listHabitsUseCase = ListHabitsUseCase(habitRepository)

    val errorHandlerUI = UIErrorHandler(console)
    val habitSelector = HabitSelector(console)

    val listHabitAction = ListHabitsAction(listHabitsUseCase, console)
    val addHabitAction = AddHabitAction(addHabitUseCase, console,errorHandlerUI)
    val habitStatisticsAction = ShowHabitStatisticsAction(calculateHabitStatisticsUseCase, console, errorHandlerUI)
    val completeHabitAction = CompleteHabitAction(completeHabitUseCase, listHabitAction, habitSelector, console, errorHandlerUI)
    val deleteHabitAction = DeleteHabitAction(deleteHabitUseCase, listHabitAction, habitSelector, console, errorHandlerUI)
    val exitAction = ExitAction(console)

    HabitTrackerController(
        listOf(addHabitAction,
        listHabitAction,
        completeHabitAction,
        deleteHabitAction,
        habitStatisticsAction,
        exitAction),
        menu
       ).run()
}

