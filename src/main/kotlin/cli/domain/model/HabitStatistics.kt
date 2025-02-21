package cli.domain.model

data class HabitStatistics(
    val totalHabits: Int,
    val completedThisWeek: Int,
    val percentageCompletion: Double,
    val bestHabit: String,
    val worstHabit: String
)