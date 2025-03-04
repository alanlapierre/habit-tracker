package com.alapierre.cli.infrastructure.ui.menu

data class MenuOption(val id: Int,
                      val descriptionKey: String,
                      val action: () -> Unit) {


    companion object {
        fun create(optionId: Int, action: () -> Unit): MenuOption {
            val descriptionKey = "menu.option.$optionId"
            return MenuOption(optionId, descriptionKey, action)
        }
    }

}
