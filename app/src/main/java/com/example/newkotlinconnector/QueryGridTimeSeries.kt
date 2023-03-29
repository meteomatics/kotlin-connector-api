package com.example.newkotlinconnector

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QueryGridTimeSeries {


    fun dataToDataFrame(response_query: Parameters): DataFrame<Coordinate> {
        val response = response_query.data
        var dataframe = emptyDataFrame<Coordinate>()

        val lat = emptyList<Double>().toMutableList()
        val lon = emptyList<Double>().toMutableList()
        val validdate = emptyList<LocalDateTime>().toMutableList()
        var parameter = emptyList<Double?>().toMutableList()

        for (i in 0 until response[0].coordinates.size) {
            for (j in 0 until response[0].coordinates[i].dates.size) {
                lat.add(response[0].coordinates[i].lat)
                lon.add(response[0].coordinates[i].lon)
                validdate.add(
                    LocalDateTime.parse(
                        response[0].coordinates[i].dates[j].validdate,
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                )
                if (response[0].coordinates[i].dates[j].value.toInt() in Constants().nA_VALUES) {
                    parameter.add(java.lang.Double.NaN)
                } else {
                    parameter.add(response[0].coordinates[i].dates[j].value)
                }
            }
        }
        dataframe = dataframe.insert(lat.toColumnOf<Double>("lat")).at(0)
        dataframe = dataframe.insert(lon.toColumnOf<Double>("lon")).at(1)
        dataframe = dataframe.insert(validdate.toColumnOf<LocalDateTime>("validdate")).at(2)
        dataframe = dataframe.insert(parameter.toColumnOf<Double?>(response[0].parameter)).at(3)
        if (response.size > 1) {
            for (k in 1 until response.size) {
                parameter = mutableListOf()
                for (i in 0 until response[0].coordinates.size) {
                    for (j in 0 until response[0].coordinates[i].dates.size) {
                        if (response[k].coordinates[i].dates[j].value.toInt() in Constants().nA_VALUES) {
                            parameter.add(java.lang.Double.NaN)
                        } else {
                            parameter.add(response[k].coordinates[i].dates[j].value)
                        }
                    }
                }
                dataframe = dataframe.insert(parameter.toColumnOf<Double?>(response[k].parameter))
                    .at(dataframe.columnsCount())
            }
        }
        return dataframe
    }


    suspend fun queryTimeSeries(
        coordinate_list: List<String>,
        resolutionList: List<String>,
        startdate: String,
        enddate: String,
        interval: String,
        parameters: List<String>,
        username: String,
        password: String,
        model: String? = null,
        ens_select: String? = null,
        interp_select: String? = null,
        on_invalid: String? = null,
        requestType: String = "GET"
    ): DataFrame<Coordinate> {
        val cluster_select: String? = null
        val optionalParams = listOf(model, interp_select, on_invalid, cluster_select, ens_select)
        val URL = BuildYourUrl().buildUrlGridTimeSeries(
            startdate,
            enddate,
            interval,
            parameters,
            coordinate_list,
            resolutionList
        )
        val response = QueryApi().queryApi(
            URL[0], URL[1], optionalParams,
            username,
            password,
            requestType = requestType
        )
        return QueryGridTimeSeries().dataToDataFrame(response)

    }
}