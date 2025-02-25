package cli.domain.exception

//Habit Exception

class HabitException(val errorCode: String) : RuntimeException(errorCode)


//HabitService Exceptions

open class HabitServiceException(val key: String) : RuntimeException(key)

class HabitSaveException(key: String) : HabitServiceException(key)

class HabitFindException(key: String) : HabitServiceException(key)

class HabitCompletionException(key: String) : HabitServiceException(key)