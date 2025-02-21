package com.alapierre


import com.alapierre.cli.infrastructure.utils.Messages
import com.alapierre.cli.infrastructure.persistence.repository.HabitRepositoryImpl
import cli.domain.service.HabitService
import cli.domain.utils.Clock
import com.alapierre.cli.domain.service.HabitStatisticsCalculator
import com.alapierre.cli.infrastructure.ui.HabitTrackerApp
import com.alapierre.cli.infrastructure.ui.common.ConsoleManager
import com.alapierre.cli.infrastructure.ui.menu.Menu

fun main() {

    val messages = Messages()
    val console = ConsoleManager()
    val clock = Clock()

    val statisticsCalculator = HabitStatisticsCalculator(clock)

    val habitService = HabitService(HabitRepositoryImpl(), messages, clock, statisticsCalculator )
    HabitTrackerApp(habitService, messages, Menu(messages, console), console).run()
}

