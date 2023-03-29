package com.example.newkotlinconnector

import com.google.gson.Gson
import org.jetbrains.kotlinx.dataframe.api.at
import org.jetbrains.kotlinx.dataframe.api.emptyDataFrame
import org.jetbrains.kotlinx.dataframe.api.insert
import org.jetbrains.kotlinx.dataframe.api.toColumnOf
import org.junit.Test

import org.junit.Assert.*
import java.io.FileReader
import java.lang.Double.NaN
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun ts_query_is_correct() {
//
//        val dataFromFile = Gson().fromJson(FileReader("/Users/ldarolti/AndroidStudioProjects/NewKotlinConnector/app/src/test/java/com/example/newkotlinconnector/ts1.json"), Parameters::class.java)
//        println(dataFromFile)
//        val actualDf  = QueryTimeSeries().dataToDataFrame(dataFromFile, listOf("51.5073219,-0.1276474", "35.3954127,-97.5959882"))
//
//
//
//
//
//        var expected_df = emptyDataFrame<Date>()
//        val lat = listOf(51.507322, 51.507322, 35.395413, 35.395413)
//        val lon = listOf(-0.127647, -0.127647, -97.595988, -97.595988)
//
//        val validdate = listOf(LocalDateTime.parse("2022-11-17T12:00", DateTimeFormatter.ISO_DATE_TIME),
//            LocalDateTime.parse("2022-11-17T13:00", DateTimeFormatter.ISO_DATE_TIME),
//            LocalDateTime.parse("2022-11-17T12:00", DateTimeFormatter.ISO_DATE_TIME),
//            LocalDateTime.parse("2022-11-17T13:00", DateTimeFormatter.ISO_DATE_TIME))
//        val temp_2m_C = listOf(11.1, 11.6, -5.0, -5.2)
//        val temp_2m_K = listOf(284.3, 284.8, 268.1, 267.9)
//        val temp_2_F = listOf(NaN, NaN, NaN, NaN)
//
//        expected_df = expected_df.insert(lat.toColumnOf<Double?>("lat")).at(0)
//        expected_df = expected_df.insert(lon.toColumnOf<Double?>("lon")).at(1)
//        expected_df = expected_df.insert(validdate.toColumnOf<LocalDateTime>("validdate"))
//            .at(2)
//        expected_df = expected_df.insert(temp_2m_C.toColumnOf<Double>("t_2m:C"))
//            .at(3)
//        expected_df = expected_df.insert(temp_2m_K.toColumnOf<Double>("t_2m:K"))
//            .at(4)
//        expected_df = expected_df.insert(temp_2_F.toColumnOf<Double>("t_2m:F"))
//            .at(5)
//
////        actual_df
//
//
//        assertEquals(expected_df, actualDf)
//        assertEquals(4, 2+2)
//    }
}