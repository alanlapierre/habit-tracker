package com.alapierre.cli.infrastructure.ui

import com.alapierre.cli.infrastructure.ui.action.*
import com.alapierre.cli.infrastructure.ui.menu.Menu


class HabitTrackerController(
    private val addHabitAction: AddHabitAction,
    private val habitStatisticsAction: ShowHabitStatisticsAction,
    private val completeHabitAction: CompleteHabitAction,
    private val deleteHabitAction: DeleteHabitAction,
    private val listHabitsAction: ListHabitsAction,
    private val exitAction: ExitAction,
    private val menu: Menu
) {

    fun run() {
        menu.start(mapOf(
            1 to { addHabitAction.execute() },
            2 to { listHabitsAction.execute() },
            3 to { completeHabitAction.execute() },
            4 to { deleteHabitAction.execute() },
            5 to { habitStatisticsAction.execute() },
            6 to { exitAction.execute() }
        ))
    }

}