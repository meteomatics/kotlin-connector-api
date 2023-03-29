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

class TimeSeriesUnitTest {
    @Test
    fun ts_query_is_correct() {

        val dataFromFile_1 = Gson().fromJson(
            FileReader("/Users/ldarolti/AndroidStudioProjects/NewKotlinConnector/app/src/test/java/com/example/newkotlinconnector/ts1.json"),
            Parameters::class.java
        )
        val dataFromFile_2 = Gson().fromJson(
            FileReader("/Users/ldarolti/AndroidStudioProjects/NewKotlinConnector/app/src/test/java/com/example/newkotlinconnector/ts2.json"),
            Parameters::class.java
        )
        val actualDf_1 = QueryTimeSeries().dataToDataFrame(
            dataFromFile_1,
            listOf("51.5073219,-0.1276474", "35.3954127,-97.5959882")
        )
        val actualDf_2 = QueryTimeSeries().dataToDataFrame(
            dataFromFile_2,
            listOf("postal_CH9000", "postal_CH9050")
        )

        var expected_df_1 = emptyDataFrame<Date>()
        var expected_df_2 = emptyDataFrame<Date>()

        val lat = listOf(51.507322, 51.507322, 35.395413, 35.395413)
        val lon = listOf(-0.127647, -0.127647, -97.595988, -97.595988)
        val postal_code = listOf("postal_CH9000", "postal_CH9000", "postal_CH9050", "postal_CH9050")

        val validdate = listOf(
            LocalDateTime.parse("2022-11-17T12:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2022-11-17T13:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2022-11-17T12:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2022-11-17T13:00", DateTimeFormatter.ISO_DATE_TIME)
        )

        val temp_2m_C_1 = listOf(11.1, 11.6, -5.0, -5.2)
        val temp_2m_K_1 = listOf(284.3, 284.8, 268.1, 267.9)
        val temp_2m_F_1 = listOf(
            java.lang.Double.NaN,
            java.lang.Double.NaN,
            java.lang.Double.NaN,
            java.lang.Double.NaN
        )
        val temp_2m_C_2 = listOf(10.7, 10.9, 11.2, 13.7)
        val temp_2m_K_2 = listOf(283.8, 284.0, 284.3, 286.8)
        val temp_2m_F_2 = listOf(51.3, 51.6, 52.2, 56.7)

        expected_df_1 = expected_df_1.insert(lat.toColumnOf<Double?>("lat")).at(0)
        expected_df_1 = expected_df_1.insert(lon.toColumnOf<Double?>("lon")).at(1)
        expected_df_1 = expected_df_1.insert(validdate.toColumnOf<LocalDateTime>("validdate"))
            .at(2)
        expected_df_1 = expected_df_1.insert(temp_2m_C_1.toColumnOf<Double>("t_2m:C"))
            .at(3)
        expected_df_1 = expected_df_1.insert(temp_2m_K_1.toColumnOf<Double>("t_2m:K"))
            .at(4)
        expected_df_1 = expected_df_1.insert(temp_2m_F_1.toColumnOf<Double>("t_2m:F"))
            .at(5)

        expected_df_2 = expected_df_2.insert(postal_code.toColumnOf<String?>("postal_code")).at(0)
        expected_df_2 = expected_df_2.insert(validdate.toColumnOf<LocalDateTime>("validdate"))
            .at(1)
        expected_df_2 = expected_df_2.insert(temp_2m_C_2.toColumnOf<Double>("t_2m:C"))
            .at(2)
        expected_df_2 = expected_df_2.insert(temp_2m_K_2.toColumnOf<Double>("t_2m:K"))
            .at(3)
        expected_df_2 = expected_df_2.insert(temp_2m_F_2.toColumnOf<Double>("t_2m:F"))
            .at(4)

//        actual_df


        Assert.assertEquals(expected_df_1, actualDf_1)
        Assert.assertEquals(expected_df_2, actualDf_2)
    }
}