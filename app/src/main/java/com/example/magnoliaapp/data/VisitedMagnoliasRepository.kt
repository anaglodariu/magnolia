package com.example.magnoliaapp.data

import kotlinx.coroutines.flow.Flow

interface VisitedMagnoliasRepository {

    fun getVisitedMagnolias():
            Flow<List<VisitedMagnolia>>

    fun isVisited(
        id: Int
    ): Flow<Boolean>

    suspend fun insertVisited(
        magnoliaId: Int
    )

    suspend fun deleteVisited(
        magnoliaId: Int
    )
}