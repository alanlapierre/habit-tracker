package com.alapierre.cli.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import org.bson.Document

object MongoDBConnection {
    private const val CONNECTION_STRING = "mongodb://localhost:27017" // Cambia esto a tu conexión MongoDB
    private const val DATABASE_NAME = "habitTracker"

    private val client: MongoClient = MongoClients.create(CONNECTION_STRING)
    val database = client.getDatabase(DATABASE_NAME)

    fun getCollection(collectionName: String): MongoCollection<Document> {
        return database.getCollection(collectionName)
    }
}
