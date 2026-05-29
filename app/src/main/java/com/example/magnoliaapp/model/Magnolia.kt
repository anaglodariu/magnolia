package com.example.magnoliaapp.model

import kotlinx.serialization.Serializable

/**
 * This data class defines a Magnolia which includes:
 * - id
 * - name
 * - latitude
 * - longitude
 * - image URL
 */
@Serializable
data class Magnolia(

    val id: Int,

    val name: String,

    val latitude: Double,

    val longitude: Double,

    val imageUrl: String
)