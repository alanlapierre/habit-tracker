package cli.domain.exception

//Habit Exception

class HabitException(val errorCode: String) : RuntimeException(errorCode)


//HabitService Exceptions

open class HabitServiceException(message: String) : RuntimeException(message)

class HabitSaveException(message: String) : HabitServiceException(message)

class HabitFindException(message: String) : HabitServiceException(message)

class HabitCompletionException(message: String) : HabitServiceException(message)