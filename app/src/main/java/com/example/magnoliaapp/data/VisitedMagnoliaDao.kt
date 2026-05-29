package com.example.magnoliaapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitedMagnoliaDao {

    @Query(
        "SELECT * FROM visited_magnolias"
    )
    fun getAllVisitedMagnolias():
            Flow<List<VisitedMagnolia>>

    @Query(
        """
        SELECT EXISTS(
            SELECT 1
            FROM visited_magnolias
            WHERE magnoliaId = :id
        )
        """
    )
    fun isVisited(id: Int):
            Flow<Boolean>

    @Insert(
        onConflict =
            OnConflictStrategy.IGNORE
    )
    suspend fun insert(
        visitedMagnolia: VisitedMagnolia
    )

    @Query(
        """
        DELETE FROM visited_magnolias
        WHERE magnoliaId = :id
        """
    )
    suspend fun delete(id: Int)
}