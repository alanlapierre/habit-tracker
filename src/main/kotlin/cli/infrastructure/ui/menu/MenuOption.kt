package com.alapierre.cli.infrastructure.ui.menu

data class MenuOption(val id: Int, val descriptionKey: String, val action: () -> Unit)