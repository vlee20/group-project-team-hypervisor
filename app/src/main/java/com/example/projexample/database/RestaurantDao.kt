package com.example.projexample.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RestaurantDao {
    // Add an Restaurant entity to a table in the database.
    // We use suspend to run the function asynchronously (coroutine).
    @Insert
    suspend fun insert(profile: Restaurant)

    // Update an Restaurant entity to a table in the database. Often uses the primary key
    // We use suspend to run the function asynchronously (coroutine).
    @Update
    suspend fun update(profile: Restaurant)

    // Custom query for retrieving a single Restaurant from a table in the database using
    // its Profile id. We don't use suspend because LiveData objects are already designed
    // to work asynchronous.
    @Query("SELECT * from rest_table WHERE restId = :key")
    fun get(key: Long): LiveData<Restaurant>?

    // Custom query for retrieving all Restaurant entities from a table in the database.
    // Data is stored to a List LiveData. We don't use suspend because LiveData objects
    // are already designed to work asynchronously.
    @Query("SELECT * from rest_table ORDER BY restId DESC")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    // Custom query for deleting all entities on a table in the database
    // We use suspend to run the function asynchronously (coroutine).
    @Query("DELETE from rest_table")
    suspend fun clear()
}