package com.alapierre.cli.infrastructure.persistence.repository

import com.alapierre.cli.database.MongoDBConnection
import com.alapierre.cli.application.model.Habit
import com.alapierre.cli.application.repository.HabitRepository
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDate

class HabitRepositoryImpl : HabitRepository {
    private val collection: MongoCollection<Document> =
        MongoDBConnection.getCollection("habits")

    override fun add(habit: Habit) {
        val document = Document()
            .append("_id", ObjectId(habit.id))
            .append("name", habit.name)
            .append("frequencyPerWeek", habit.frequencyPerWeek)
            .append("completions", habit.completions)
        collection.insertOne(document)
    }

    override fun list(): List<Habit> {
        return collection.find()
            .map { doc ->
                Habit(
                    id = doc.getObjectId("_id").toString(),
                    name = doc.getString("name"),
                    frequencyPerWeek = doc.getInteger("frequencyPerWeek"),
                    completions = doc.getList("completions", String::class.java).toMutableList()
                )
            }.toList()
    }

    override fun delete(habitId: String) {
        collection.deleteOne(Filters.eq("_id", ObjectId(habitId)))
    }

    override fun findById(id: String): Habit? {
        val document = collection.find(Filters.eq("_id", ObjectId(id))).firstOrNull()

        return document?.let {
            Habit(
                id = it.getObjectId("_id").toString(),
                name = it.getString("name"),
                frequencyPerWeek = it.getInteger("frequencyPerWeek"),
                completions = it.getList("completions", String::class.java).toMutableList()
            )
        }
    }

    override fun addCompletion(habitId: String) {
        val completionDate = LocalDate.now().toString()
        collection.updateOne(
            Filters.eq("_id", ObjectId(habitId)),
            Updates.push("completions", completionDate)
        )
    }

}
