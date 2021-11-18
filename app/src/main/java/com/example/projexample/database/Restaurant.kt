package com.example.projexample.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rest_table")
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    var restId: Long = 0L,

    @ColumnInfo
    var name: String = ""
)