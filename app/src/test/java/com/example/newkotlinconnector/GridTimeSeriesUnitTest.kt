package com.example.newkotlinconnector

import com.google.gson.Gson
import org.jetbrains.kotlinx.dataframe.api.at
import org.jetbrains.kotlinx.dataframe.api.emptyDataFrame
import org.jetbrains.kotlinx.dataframe.api.insert
import org.jetbrains.kotlinx.dataframe.api.toColumnOf
import org.junit.Assert
import org.junit.Test
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GridTimeSeriesUnitTest {
    @Test
    fun grid_ts_query_is_correct() {

        val dataFromFile = Gson().fromJson(
            FileReader("/Users/ldarolti/AndroidStudioProjects/NewKotlinConnector/app/src/test/java/com/example/newkotlinconnector/gridts1.json"),
            Parameters::class.java
        )
        println(dataFromFile)
        val actualDf = QueryGridTimeSeries().dataToDataFrame(dataFromFile)

        var expected_df = emptyDataFrame<Date>()
        val lat = listOf(48.0, 48.0, 48.0, 48.0, 50.0, 50.0, 50.0, 50.0)
        val lon = listOf(0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 2.0, 2.0)
        val validdate = listOf(
            LocalDateTime.parse("2021-03-02T00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2021-03-02T03:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2021-03-02T00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2021-03-02T03:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2021-03-02T00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2021-03-02T03:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2021-03-02T00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2021-03-02T03:00", DateTimeFormatter.ISO_DATE_TIME)
        )
        val t_2m_C = listOf(4.6, 4.1, 5.0, 4.7, 8.1, 7.8, 1.7, 0.7)
        val precip_1h_mm = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val precip_3h_mm = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)


        expected_df = expected_df.insert(lat.toColumnOf<Double>("lat")).at(0)
        expected_df = expected_df.insert(lon.toColumnOf<Double>("lon")).at(1)
        expected_df = expected_df.insert(validdate.toColumnOf<LocalDateTime>("validdate")).at(2)
        expected_df = expected_df.insert(t_2m_C.toColumnOf<Double?>("t_2m:C")).at(3)
        expected_df = expected_df.insert(precip_1h_mm.toColumnOf<Double?>("precip_1h:mm")).at(4)
        expected_df = expected_df.insert(precip_3h_mm.toColumnOf<Double?>("precip_3h:mm")).at(5)

        Assert.assertEquals(expected_df, actualDf)
    }
}