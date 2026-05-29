package com.example.magnoliaapp.data

import com.example.magnoliaapp.model.Magnolia
import com.example.magnoliaapp.network.MagnoliaApiService

/**
 * Repository that fetches magnolias data from MagnoliaApi.
 */
interface MagnoliasRepository {

    /** Fetches list of Magnolia from MagnoliaApi */
    suspend fun getMagnolias(): List<Magnolia>

    /** Fetches a single Magnolia by id */
    suspend fun getMagnolia(id: Int): Magnolia
}

/**
 * Network implementation of Repository that fetches magnolias data
 * from MagnoliaApi.
 */
class NetworkMagnoliasRepository(
    private val magnoliaApiService: MagnoliaApiService
) : MagnoliasRepository {

    /** Fetches list of Magnolia from MagnoliaApi */
    override suspend fun getMagnolias(): List<Magnolia> =
        magnoliaApiService.getMagnolias()

    /** Fetches a single Magnolia from MagnoliaApi */
    override suspend fun getMagnolia(id: Int): Magnolia =
        magnoliaApiService.getMagnolia(id)
}