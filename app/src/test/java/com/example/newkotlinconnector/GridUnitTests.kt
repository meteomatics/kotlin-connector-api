package com.example.newkotlinconnector

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import java.io.FileReader

class GridUnitTests {
    val dataFromFile = Gson().fromJson(
        FileReader("src/test/testData/grid1.json"),
        Parameters::class.java
    )
    val actualDf = QueryGrid().dataToDataFrame(dataFromFile)


    @Test
    fun first_column_is_corectly_parsed() {
        val lon_lat = listOf(-97.0, -96.0, -95.0)
        Assert.assertEquals(lon_lat, actualDf["lon/lat"].toList())
    }


    @Test
    fun second_column_is_correctly_parsed() {
        val lat_48 = listOf(-4.0, -7.7, -7.3)
        Assert.assertEquals(lat_48, actualDf["48.0"].toList())
    }

    @Test
    fun third_column_is_correctly_parsed() {
        val lat_49 = listOf(-4.9, -7.2, -4.9)
        Assert.assertEquals(lat_49, actualDf["49.0"].toList())
    }

    @Test
    fun fourth_column_is_correctly_parsed() {
        val lat_50 = listOf(-4.4, -8.3, -6.5)
        Assert.assertEquals(lat_50, actualDf["50.0"].toList())
    }
}