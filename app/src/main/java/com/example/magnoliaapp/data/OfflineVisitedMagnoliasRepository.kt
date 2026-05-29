package com.example.magnoliaapp.data

import kotlinx.coroutines.flow.Flow

class OfflineVisitedMagnoliasRepository(

    private val dao:
    VisitedMagnoliaDao

) : VisitedMagnoliasRepository {

    override fun getVisitedMagnolias():
            Flow<List<VisitedMagnolia>> =
        dao.getAllVisitedMagnolias()

    override fun isVisited(id: Int):
            Flow<Boolean> =
        dao.isVisited(id)

    override suspend fun insertVisited(
        magnoliaId: Int
    ) {

        dao.insert(
            VisitedMagnolia(
                magnoliaId = magnoliaId
            )
        )
    }

    override suspend fun deleteVisited(
        magnoliaId: Int
    ) {

        dao.delete(magnoliaId)
    }
}