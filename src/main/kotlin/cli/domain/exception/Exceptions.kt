package cli.domain.exception

open class HabitServiceException(message: String) : RuntimeException(message)

class HabitNotFoundException(message: String) : HabitServiceException(message)

class HabitSaveException(message: String) : HabitServiceException(message)

class HabitCompletionException(message: String) : HabitServiceException(message)