package com.example.newkotlinconnector
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.newkotlinconnector.databinding.ActivityMainBinding
import com.facebook.stetho.Stetho
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Examples  : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            val communityUsername = "kotlin-community"
            val communityPassword = "s9qz-nJ7uEg+"
            val myUsername = ""
            val myPassword = ""



///////////////////////////////////// FOR COMMUNITY USERS /////////////////////////////////////////
//            /////////////////// EXAMPLE TIME SERIES /////////////////////////////////////////
            // choose coordinates
            val coordinates = listOf("51.5073219,-0.1276474", "35.3954127,-97.5959882")
            // choose time interval: 3 hours
            val timeInterval = "PT3H"
            // choose parameters
            val parameters = listOf("t_2m:C", "t_2m:K", "t_2m:F")
            // choose model
            val model = "mix"
            // formatter for date and time
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.000Z'")
            // choose start date
            val now: LocalDateTime = LocalDateTime.now()
            // choose end date
            val tomorrow: LocalDateTime = now.plusDays(1)
            // make a query to the API
            val dfTimeseries1 = QueryTimeSeries()
                .queryTimeSeries(coordinates, now.format(formatter), tomorrow.format(formatter),
                    timeInterval, parameters, communityUsername, communityPassword,
                    model = model, on_invalid = "fill_with_invalid")
            println("first time series:")
            println(dfTimeseries1)

            val dfTimeseries2 = QueryTimeSeries()
                .queryTimeSeries(
                    listOf("postal_CH9000", "postal_CH9050"),
                    now.format(formatter),
                    tomorrow.format(formatter),
                    "PT1H",
                    listOf("t_2m:C", "t_2m:K", "t_2m:F"), communityUsername, communityPassword,
                   on_invalid = "fill_with_invalid"
                )
            println("second time series:")
            println(dfTimeseries2)

            // select only one location
            println("TS1: value at this lat and lon")
            val smallerDF1 = dfTimeseries1.getTSatThisLatAndLon(51.507322,-0.127647)
            println(smallerDF1)

            println("TS2: value at this lat and lon")
            val smallerDF2 = dfTimeseries2.getTSatThisPostCode("postal_CH9050")
            println(smallerDF2)




//            ////////////////////////////////// EXAMPLE GRID ///////////////////////////////////////
//            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.000Z'")
//            val now: LocalDateTime = LocalDateTime.now()
            val dfGrid = QueryGrid().queryGrid(
                listOf("51.50,-97.59", "40,-0.60"),
                listOf("2", "2"),
                now.format(formatter),
                "t_2m:C",
                communityUsername, communityPassword)
            println("grid")
            println(dfGrid)

            val gridValAtThisLat = dfGrid["42.0"]
            val gridValAtThisLon = dfGrid.getValuesByLongitude(-83.59)
            val gridValAtThisLatAndLon = dfGrid.getValueAtThisLatAndLon(42.0, -83.59)
            Log.d("grid value at this lat", gridValAtThisLat.toString())
            Log.d("grid value at this lon", gridValAtThisLon.toString())
            Log.d("grid value at this lat and lon", gridValAtThisLatAndLon.toString())




//            ////////////////////////////// EXAMPLE GRID TIME SERIES ///////////////////////////////
//            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.000Z'")
//            val now: LocalDateTime = LocalDateTime.now()
//            val tomorrow: LocalDateTime = now.plusDays(1)
            val dfGridTS = QueryGridTimeSeries()
                .queryTimeSeries(
                    listOf("50,-5", "40,5"),
                    listOf("2", "2"),
                    now.format(formatter),
                    tomorrow.format(formatter),
                    "PT3H",
                    listOf("t_2m:C", "precip_1h:mm", "precip_3h:mm"),
                    communityUsername, communityPassword
                )

            println("this is the grid time series")
            println(dfGridTS.toString())
            println("df at this latitude")
            println(dfGridTS.getDfAtThisLat(40.0))
            println("df at this lat and lon")
            println(dfGridTS.getDfAtThisLatAndLon(40.0, -3.0))
            println("precip_3h:mm at this lat and lon")
            println(dfGridTS.getDfAtThisLatAndLon(40.0, -3.0)["precip_3h:mm"])











/////////////////////////////////////// FOR FULL USERS ////////////////////////////////////////////
//            ///////////////////// EXAMPLE TIME SERIES /////////////////////////////////////////
            val dfTimeseries12 = QueryTimeSeries()
                .queryTimeSeries(
                    listOf("51.5073219,-0.1276474", "35.3954127,-97.5959882"),
                    "2022-11-17T12:00:00.000Z",
                    "2022-11-18T18:00:00.000Z",
                    "PT5M",
                    listOf("t_2m:C", "t_2m:K", "t_2m:F"), myUsername, myPassword,
                    model = "mix-obs", on_invalid = "fill_with_invalid"
                )

            val dfTimeseries22 = QueryTimeSeries()
                .queryTimeSeries(
                    listOf("postal_CH9000", "postal_CH9050"),
                    "2022-11-17T12:00:00.000Z",
                    "2022-11-18T18:00:00.000Z",
                    "PT5M",
                    listOf("t_2m:C", "t_2m:K", "t_2m:F"), myUsername, myPassword,
                    model = "mix-obs", on_invalid = "fill_with_invalid"
                )
            println("first time series:")
            println(dfTimeseries12)
            println("second time series:")
            println(dfTimeseries22)

            // select only one location
            println("TS1: value at this lat and lon")
            val smallerDF12 = dfTimeseries12.getTSatThisLatAndLon(51.507321,-0.127647)
            println(smallerDF12)

            println("TS2: value at this lat and lon")
            val smallerDF22 = dfTimeseries22.getTSatThisPostCode("postal_CH9050")
            println(smallerDF22)




//            ////////////////////////////////// EXAMPLE GRID ///////////////////////////////////////
            val dfGrid2 = QueryGrid().queryGrid(
                listOf("51.50,-97.59", "40,-0.60"),
                listOf("2", "2"),
                "2022-11-10T12:00:00.000Z",
                "t_2m:C",
                myUsername,
                myPassword
            )
            println("grid")
            println(dfGrid2)

            val gridValAtThisLat2 = dfGrid2["42.0"]
            val gridValAtThisLon2 = dfGrid2.getValuesByLongitude(-83.59)
            val gridValAtThisLatAndLon2 = dfGrid2.getValueAtThisLatAndLon(42.0, -83.59)
            Log.d("grid value at this lat", gridValAtThisLat2.toString())
            Log.d("grid value at this lon", gridValAtThisLon2.toString())
            Log.d("grid value at this lat and lon", gridValAtThisLatAndLon2.toString())




//            ////////////////////////////// EXAMPLE GRID TIME SERIES ///////////////////////////////
            val dfGridTS2 = QueryGridTimeSeries()
                .queryTimeSeries(
                    listOf("50,-5", "40,5"),
                    listOf("2", "2"),
                    "2021-03-02T00:00:00+00:00",
                    "2021-03-03T00:00:00+00:00",
                    "PT3H",
                    listOf("t_2m:C", "precip_1h:mm", "precip_3h:mm"),
                    myUsername, myPassword
                )

            println("this is the grid time series")
            println(dfGridTS2.toString())
            println("df at this latitude")
            println(dfGridTS2.getDfAtThisLat(40.0))
            println("df at this lat and lon")
            println(dfGridTS2.getDfAtThisLatAndLon(40.0, -3.0))
            println("precip_3h:mm at this lat and lon")
            println(dfGridTS2.getDfAtThisLatAndLon(40.0, -3.0)["precip_3h:mm"])

        }
    }
}