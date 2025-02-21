package cli.domain.model

import cli.domain.exception.HabitException
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDate

@Serializable
data class Habit(
        val id: String = ObjectId().toString(),
        val name: String,
        val frequencyPerWeek: Int,
        val completions: MutableList<String> = mutableListOf()
) {
        init {
                if (name.isEmpty() || name.isBlank())
                        throw HabitException(errorCode = "INVALID_NAME")

                if (frequencyPerWeek <= 0)
                        throw HabitException(errorCode = "INVALID_FREQUENCY")
        }


        companion object {
                fun validateId(id: String) {
                        if (!ObjectId.isValid(id)) {
                                throw HabitException(errorCode = "INVALID_ID")
                        }
                }
        }

        fun addCompletion(completionDate: LocalDate) {

                completionDate.toString().let { date ->
                        if (completions.contains(date))
                                throw HabitException(errorCode = "ALREADY_COMPLETED")

                        completions.add(date)
                }
        }

}
