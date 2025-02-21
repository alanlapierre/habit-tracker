package cli.domain.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Habit(
        val id: String = ObjectId().toString(),
        val name: String,
        val frequencyPerWeek: Int,
        val completions: MutableList<String> = mutableListOf()
)
