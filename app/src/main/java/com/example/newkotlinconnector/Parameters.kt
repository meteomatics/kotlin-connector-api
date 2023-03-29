package com.example.newkotlinconnector

import com.google.gson.annotations.SerializedName


data class Parameters(
    val `data`: List<Data>,
    val dateGenerated: String,
    val status: String,
    val user: String,
    val version: String
)

data class Data(
    val coordinates: List<Coordinate>,
    val parameter: String
)

data class Coordinate(
    val dates: List<Date>,
    val lat: Double,
    val lon: Double,
    val station_id: String

)

data class Date(
    @SerializedName("date") val validdate: String,
    val value: Double
)