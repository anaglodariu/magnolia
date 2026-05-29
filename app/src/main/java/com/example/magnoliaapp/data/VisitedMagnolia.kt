package com.example.magnoliaapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visited_magnolias")
data class VisitedMagnolia(

    @PrimaryKey
    val magnoliaId: Int
)