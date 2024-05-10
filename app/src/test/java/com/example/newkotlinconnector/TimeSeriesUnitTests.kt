package com.example.newkotlinconnector

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeSeriesUnitTests {
    val dataFromFile_1 = Gson().fromJson(
        FileReader("src/test/testData/ts1.json"), Parameters::class.java
    )
    val dataFromFile_2 = Gson().fromJson(
        FileReader("src/test/testData/ts2.json"), Parameters::class.java
    )
    val actualDf_1 = QueryTimeSeries().dataToDataFrame(
        dataFromFile_1, listOf("51.5073219,-0.1276474", "35.3954127,-97.5959882")
    )
    val actualDf_2 = QueryTimeSeries().dataToDataFrame(
        dataFromFile_2, listOf("postal_CH9000", "postal_CH9050")
    )


    @Test
    fun lat_lon_are_correctly_parsed() {
        val lat = listOf(51.507322, 51.507322, 35.395413, 35.395413)
        val lon = listOf(-0.127647, -0.127647, -97.595988, -97.595988)

        Assert.assertEquals(lat, actualDf_1["lat"].toList())
        Assert.assertEquals(lon, actualDf_1["lon"].toList())
    }


    @Test
    fun post_code_is_correctly_parsed() {
        val postal_code = listOf("postal_CH9000", "postal_CH9000", "postal_CH9050", "postal_CH9050")
        Assert.assertEquals(postal_code, actualDf_2["postal_code"].toList())
    }


    @Test
    fun validdate_is_correctly_parsed() {
        val validdate = listOf(
            LocalDateTime.parse("2022-11-17T12:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2022-11-17T13:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2022-11-17T12:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2022-11-17T13:00", DateTimeFormatter.ISO_DATE_TIME)
        )
        Assert.assertEquals(validdate, actualDf_1["validdate"].toList())
        Assert.assertEquals(validdate, actualDf_2["validdate"].toList())
    }


    @Test
    fun temperature__degree_C_is_correctly_parsed() {
        val temp_2m_C_1 = listOf(11.1, 11.6, -5.0, -5.2)
        val temp_2m_C_2 = listOf(10.7, 10.9, 11.2, 13.7)

        Assert.assertEquals(temp_2m_C_1, actualDf_1["t_2m:C"].toList())
        Assert.assertEquals(temp_2m_C_2, actualDf_2["t_2m:C"].toList())
    }


    @Test
    fun temperature__degree_K_is_correctly_parsed() {
        val temp_2m_K_1 = listOf(284.3, 284.8, 268.1, 267.9)
        val temp_2m_K_2 = listOf(283.8, 284.0, 284.3, 286.8)

        Assert.assertEquals(temp_2m_K_1, actualDf_1["t_2m:K"].toList())
        Assert.assertEquals(temp_2m_K_2, actualDf_2["t_2m:K"].toList())
    }


    @Test
    fun temperature__degree_F_is_correctly_parsed() {
        val temp_2m_F_1 = listOf(
            java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN
        )
        val temp_2m_F_2 = listOf(51.3, 51.6, 52.2, 56.7)

        Assert.assertEquals(temp_2m_F_1, actualDf_1["t_2m:F"].toList())
        Assert.assertEquals(temp_2m_F_2, actualDf_2["t_2m:F"].toList())
    }
}