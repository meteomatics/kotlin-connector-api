package com.example.newkotlinconnector

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import java.lang.Double.NaN

class QueryGrid {

    fun dataToDataFrame(query_response: Parameters): DataFrame<Coordinate> {
        val response = query_response.data
        var dataframe = emptyDataFrame<Coordinate>()
        var newColumn = emptyList<Double>().toMutableList()
        var oldlat: Double? = null
        var columnCounter = 0
        var newlat = 0.0
        val longitude = emptyList<Double>().toMutableList()
        for (i in 0 until response[0].coordinates.size) {
            newlat = response[0].coordinates[i].lat
            if (newlat != oldlat) {
                if (columnCounter == 0) {
                    newColumn = mutableListOf()
                } else {
                    dataframe = dataframe.insert(newColumn.toColumnOf<Double?>("${oldlat}"))
                        .at(dataframe.columnsCount())
                    newColumn = mutableListOf()
                }
                columnCounter += 1
            } else {
                if (columnCounter == 1) {
                    longitude.add(response[0].coordinates[i].lon)
                }
                if (response[0].coordinates[i].dates[0].value.toInt() in Constants().nA_VALUES) {
                    newColumn.add(NaN)
                } else {
                    newColumn.add(response[0].coordinates[i].dates[0].value)
                }
            }
            oldlat = newlat
        }
        dataframe = dataframe.insert(longitude.toColumnOf<Double>("lon/lat")).at(0)
        return dataframe
    }


    suspend fun queryGrid(
        coordinateList: List<String>,
        resolutionList: List<String>,
        startdate: String,
        parameter: String,
        username: String,
        password: String,
        model: String? = null,
        ens_select: String? = null,
        interp_select: String? = null,
        requestType: String = "GET"
    ): DataFrame<Coordinate> {
        val on_invalid: String? = null
        val cluster_select: String? = null
        val optionalParams = listOf(model, interp_select, on_invalid, cluster_select, ens_select)
        val URL = BuildYourUrl().buildUrlGrid(
            startdate,
            parameter,
            coordinateList,
            resolutionList
        )
        val response = QueryApi().queryApi(
            URL[0], URL[1], optionalParams,
            username,
            password,
            requestType = requestType
        )

        return QueryGrid().dataToDataFrame(response)
    }
}