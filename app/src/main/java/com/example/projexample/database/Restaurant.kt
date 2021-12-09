package com.example.projexample.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rest_table")
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    var restId: Long = 0L,

    @ColumnInfo
    var name: String = "",

    @ColumnInfo
    var address: String = "",

    @ColumnInfo
    var phone: String = "",

    @ColumnInfo
    var distance: Double = 0.0,

    @ColumnInfo
    var category: String = "",

    @ColumnInfo
    var rating: Double = 0.0
)