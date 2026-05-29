
package com.example.magnoliaapp.network

import com.example.magnoliaapp.model.Magnolia
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A public interface that exposes the [getMagnolias] method.
 */
interface MagnoliaApiService {

    /**
     * Returns a [List] of [Magnolia] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "magnolias" endpoint
     * will be requested with the GET HTTP method.
     */
    @GET("magnolias")
    suspend fun getMagnolias(): List<Magnolia>

    @GET("magnolias/{id}")
    suspend fun getMagnolia(
        @Path("id") id: Int
    ): Magnolia
}