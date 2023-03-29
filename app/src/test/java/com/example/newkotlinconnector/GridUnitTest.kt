package com.example.newkotlinconnector

import com.google.gson.Gson
import org.jetbrains.kotlinx.dataframe.api.at
import org.jetbrains.kotlinx.dataframe.api.emptyDataFrame
import org.jetbrains.kotlinx.dataframe.api.insert
import org.jetbrains.kotlinx.dataframe.api.toColumnOf
import org.junit.Assert
import org.junit.Test
import java.io.FileReader
import java.lang.Double.NaN
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GridUnitTest {
    @Test
    fun grid_query_is_correct() {

        val dataFromFile = Gson().fromJson(
            FileReader("/Users/ldarolti/AndroidStudioProjects/NewKotlinConnector/app/src/test/java/com/example/newkotlinconnector/grid1.json"),
            Parameters::class.java
        )
        println(dataFromFile)
        val actualDf = QueryGrid().dataToDataFrame(dataFromFile)

        var expected_df = emptyDataFrame<Date>()
        val lon_lat = listOf(-97.0, -96.0, -95.0)
        val lat_48 = listOf(-4.0, -7.7, -7.3)
        val lat_49 = listOf(-4.9, -7.2, -4.9)
        val lat_50 = listOf(-4.4, -8.3, -6.5)


        expected_df = expected_df.insert(lon_lat.toColumnOf<Double>("lon/lat")).at(0)
        expected_df = expected_df.insert(lat_48.toColumnOf<Double?>("48.0")).at(1)
        expected_df = expected_df.insert(lat_49.toColumnOf<Double?>("49.0")).at(2)
        expected_df = expected_df.insert(lat_50.toColumnOf<Double?>("50.0")).at(3)

        Assert.assertEquals(expected_df, actualDf)
    }
}