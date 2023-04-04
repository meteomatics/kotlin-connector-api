package com.example.newkotlinconnector

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QueryTimeSeries {
    fun dataToDataFrame(
        query_response: Parameters,
        coordinate_list: List<String>
    ): DataFrame<Date> {
        val response = query_response.data

        // add first set of coordinates
        var dataframe = emptyDataFrame<Date>()

        // add lat and lon if coordinates
        // add postal_code if postal code
        // only works for all postal codes or all coordinates
        if (coordinate_list[0].startsWith("postal_")) {
            val postal_code = emptyList<String?>().toMutableList()
            for (i in 0 until response[0].coordinates[0].dates.size) {
                postal_code.add(response[0].coordinates[0].station_id)
            }

            // add rest of coordinates
            if (response[0].coordinates.size > 1) {
                for (j in 1 until response[0].coordinates.size) {
                    for (i in 0 until response[0].coordinates[j].dates.size) {
                        postal_code.add(response[0].coordinates[j].station_id)
                    }
                }
            }
            dataframe = dataframe.insert(postal_code.toColumnOf<String?>("postal_code")).at(0)

        } else {
            val latitude = emptyList<Double?>().toMutableList()
            val longitude = emptyList<Double?>().toMutableList()
            for (i in 0 until response[0].coordinates[0].dates.size) {
                latitude.add(response[0].coordinates[0].lat)
                longitude.add(response[0].coordinates[0].lon)
            }
            // add rest of coordinates
            if (response[0].coordinates.size > 1) {
                for (j in 1 until response[0].coordinates.size) {
                    for (i in 0 until response[0].coordinates[j].dates.size) {
                        latitude.add(response[0].coordinates[j].lat)
                        longitude.add(response[0].coordinates[j].lon)
                    }
                }
            }
            dataframe = dataframe.insert(latitude.toColumnOf<Double?>("lat")).at(0)
            dataframe = dataframe.insert(longitude.toColumnOf<Double?>("lon")).at(1)
        }

        val validdate = emptyList<LocalDateTime>().toMutableList()
        var parameter: MutableList<Double?>
        // add validdate to DF
        for (j in 0 until response[0].coordinates.size) {
            for (i in 0 until response[0].coordinates[j].dates.size) {
                validdate.add(
                    LocalDateTime.parse(
                        response[0].coordinates[j].dates[i].validdate,
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                )
            }
        }
        dataframe = dataframe.insert(validdate.toColumnOf<LocalDateTime>("validdate"))
            .at(dataframe.columnsCount())

        // add parameters to DF
        for (k in response.indices) {
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
            dataframe = dataframe.insert(parameter.toColumnOf<Double>(response[k].parameter))
                .at(dataframe.columnsCount())
        }
        return dataframe
    }


    suspend fun queryTimeSeries(
        coordinate_list: List<String>,
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
        requestType: String = "GET",
        cluster_select: String? = null
    ): DataFrame<Date> {
        val optionalParams = listOf(model, interp_select, on_invalid, cluster_select, ens_select)
        val URL = BuildYourUrl().buildUrlTimeSeries(
            startdate,
            enddate,
            interval,
            parameters,
            coordinate_list
        )
        val response = QueryApi().queryApi(
            URL[0], URL[1], optionalParams,
            username,
            password,
            requestType = requestType
        )

        return QueryTimeSeries().dataToDataFrame(response, coordinate_list)
    }
}