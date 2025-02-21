package com.alapierre.cli.infrastructure.ui.menu

data class MenuOption(val id: Int, val description: String, val action: () -> Unit)