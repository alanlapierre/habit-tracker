package com.alapierre.cli.infrastructure.ui.action

import com.alapierre.cli.domain.dto.HabitResponseDto

sealed interface ActionResult

data object NoResult: ActionResult

data class ListResult(val habits: List<HabitResponseDto>): ActionResult