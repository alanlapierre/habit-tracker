package com.alapierre.cli.infrastructure.ui

import com.alapierre.cli.infrastructure.ui.action.*
import com.alapierre.cli.infrastructure.ui.menu.Menu


class HabitTrackerController(
    private val habitActions: List<HabitAction>,
    private val menu: Menu
) {

    fun run() {

        val actionMap = habitActions.mapIndexed { index, action ->
            index + 1 to { action.execute() }
        }.toMap()

        menu.start(actionMap)
    }
}