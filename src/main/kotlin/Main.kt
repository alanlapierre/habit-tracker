package com.alapierre


import com.alapierre.cli.infrastructure.utils.Messages
import com.alapierre.cli.infrastructure.persistence.repository.HabitRepositoryImpl
import cli.domain.service.HabitService
import cli.domain.utils.Clock
import com.alapierre.cli.infrastructure.ui.HabitTrackerApp
import com.alapierre.cli.infrastructure.ui.common.ConsoleManager
import com.alapierre.cli.infrastructure.ui.menu.Menu

fun main() {

    val messages = Messages()
    val console = ConsoleManager()

    val habitService = HabitService(HabitRepositoryImpl(), messages,  Clock())
    HabitTrackerApp(habitService, messages, Menu(messages, console), console).run()
}

