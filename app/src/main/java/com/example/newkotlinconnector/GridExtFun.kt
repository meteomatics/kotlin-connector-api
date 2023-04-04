package com.example.newkotlinconnector

import org.jetbrains.kotlinx.dataframe.DataFrame

// extension functions for grid
fun DataFrame<Coordinate>.getValuesByLongitude(lon: Double): List<Double?> {
    val index = this["lon/lat"].toList().indexOf(lon)
    return this[index].values()
        .subList(1, this.columnsCount()) as List<Double?>//=> select all but the first value
}

fun DataFrame<Coordinate>.getValueAtThisLatAndLon(lat: Double, lon: Double): Double? {
    val index = this["lon/lat"].toList().indexOf(lon)
    return this[lat.toString()][index] as Double?
}

// extension functions for grid ts
fun DataFrame<Coordinate>.getDfAtThisLat(lat: Double): DataFrame<Coordinate?> {
    val index = this["lat"].toList().indexOf(lat)
    val lastIndex = this["lat"].toList().lastIndexOf(lat)
    return this[index..lastIndex]
}

fun DataFrame<Coordinate>.getDfAtThisLatAndLon(lat: Double, lon: Double): DataFrame<Coordinate?> {
    val index = this["lat"].toList().indexOf(lat)
    val lastIndex = this["lat"].toList().lastIndexOf(lat)
    val df = this[index..lastIndex]
    val firstIndexForLon = df["lon"].toList().indexOf(lon)
    val lastIndexForLon = df["lon"].toList().lastIndexOf(lon)
    return df[firstIndexForLon..lastIndexForLon]
}

// extension functons for TS
fun DataFrame<Date>.getTSatThisLatAndLon(lat: Double, lon: Double): DataFrame<Date> {
    val firstindex = kotlin.math.max(
        this["lat"].toList().indexOf(lat),
        this["lon"].toList().indexOf(lon))
    val lastindex = kotlin.math.min(
        this["lat"].toList().lastIndexOf(lat),
        this["lon"].toList().lastIndexOf(lon))
    return this[firstindex..lastindex]
}

fun DataFrame<Date>.getTSatThisPostCode(post_code: String): DataFrame<Date> {
    val firstindex = this["postal_code"].toList().indexOf(post_code)
    val lastindex = this["postal_code"].toList().lastIndexOf(post_code)
    return this[firstindex..lastindex]
}