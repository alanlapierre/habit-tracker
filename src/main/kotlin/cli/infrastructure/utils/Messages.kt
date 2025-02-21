package com.alapierre.cli.infrastructure.utils

import java.util.*

class Messages(language: String = "es", country: String = "ES") {
    private val bundle: ResourceBundle = ResourceBundle.getBundle("messages", Locale(language, country))

    fun get(key: String): String {
        return bundle.getString(key)
    }
}