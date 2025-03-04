package com.alapierre.cli.infrastructure.ui.menu.presenter

import com.alapierre.cli.infrastructure.ui.menu.MenuOption

interface MenuPresenter {
    fun displayMenu(options: Map<Int, MenuOption>)
}