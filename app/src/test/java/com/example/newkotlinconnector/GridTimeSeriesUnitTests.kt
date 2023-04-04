package com.example.newkotlinconnector

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GridTimeSeriesUnitTests {

    val dataFromFile = Gson().fromJson(
        FileReader("src/main/res/testData/gridts1.json"),
        Parameters::class.java
    )
    val actualDf = QueryGridTimeSeries().dataToDataFrame(dataFromFile)


    @Test
    fun lat_lon_are_correctly_parsed() {
        val lat = listOf(48.0, 48.0, 48.0, 48.0, 50.0, 50.0, 50.0, 50.0)
        val lon = listOf(0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 2.0, 2.0)

        Assert.assertEquals(lat, actualDf["lat"].toList())
        Assert.assertEquals(lon, actualDf["lon"].toList())
    }


    @Test
    fun validdate_is_correctly_parsed() {
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

        Assert.assertEquals(validdate, actualDf["validdate"].toList())
    }


    @Test
    fun temp_is_correctly_parsed() {
        val t_2m_C = listOf(4.6, 4.1, 5.0, 4.7, 8.1, 7.8, 1.7, 0.7)

        Assert.assertEquals(t_2m_C, actualDf["t_2m:C"].toList())
    }


    @Test
    fun precipitation_is_correctly_parsed() {
        val precip_1h_mm = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val precip_3h_mm = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        Assert.assertEquals(precip_1h_mm, actualDf["precip_1h:mm"].toList())
        Assert.assertEquals(precip_3h_mm, actualDf["precip_3h:mm"].toList())
    }
}