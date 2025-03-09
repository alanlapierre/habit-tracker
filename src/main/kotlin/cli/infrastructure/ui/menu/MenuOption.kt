package com.alapierre.cli.infrastructure.ui.menu

import com.alapierre.cli.infrastructure.ui.action.ActionResult

data class MenuOption(val id: Int, val descriptionKey: String, val action: () -> ActionResult) {

    companion object {
        fun create(optionId: Int, action: () -> ActionResult): MenuOption {
            val descriptionKey = "menu.option.$optionId"
            return MenuOption(optionId, descriptionKey, action)
        }
    }

}
