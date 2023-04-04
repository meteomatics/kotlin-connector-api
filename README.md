
<!-- README.md is generated from README.Rmd. Please edit that file -->

[![logo](https://static.meteomatics.com/meteomatics-logo.png)](https://www.meteomatics.com)

# Kotlin connector to the [Meteomatics API](https://api.meteomatics.com/Overview.html "Documentation Overwiev")

<!-- badges: start -->
<!-- badges: end -->

===================================================================================

Meteomatics provides a REST-style API to retrieve historic, current, and
forecast data globally. This includes model data and observational data
in time series and areal formats. Areal formats are also offered through
a WMS/WFS-compatible interface. Geographic and time series data can be
combined in certain file formats, such as NetCDF.

## Installation

You can install the development version of Meteomatics Kotlin Connector from
[GitHub](https://github.com/meteomatics/kotlin-connector-api) by cloning the
git repository to a local directory of your choice; then the downloaded functions 
can be used.

## Requirements

The following external Kotlin packages are required and need to be
imported inside the project:

com.example.newkotlinconnector, android.os.Bundle, android.util.Log,
androidx.appcompat.app.AppCompatActivity, androidx.lifecycle.lifecycleScope, 
com.example.newkotlinconnector.databinding.ActivityMainBinding, 
com.facebook.stetho.Stetho, java.time.LocalDateTime, java.time.format.DateTimeFormatter.

The following external Kotlin packages are required and need to be
added to the build.gradle file in the field called dependencies:
```
    // stetho
    implementation 'com.facebook.stetho:stetho:1.5.1'
    implementation 'com.facebook.stetho:stetho-okhttp3:1.6.0'
    // retrofit: library for api call
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    // http client
    implementation "io.ktor:ktor-client-apache:2.1.3"
    // GSON
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.google.code.gson:gson:2.8.6'
    // coroutine
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4'
    // read JSON file
    implementation 'com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2'
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    // KOTLIN DATAFRAME
    implementation 'org.jetbrains.kotlinx:dataframe:0.8.1'
    //DATE AND TIME
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
```

## Examples

This is a basic example on how to use the `queryTimeSeries()` function:


``` Kotlin
class Examples  : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this);
        
        // create coroutine to use the functions: needed anywhere 
        // where you use the functions
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            val communityUsername = "kotlin-community"
            val communityPassword = "s9qz-nJ7uEg+"



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
            val smallerDF1 = dfTimeseries1.getTSatThisLatAndLon(51.507321,-0.127647)
            println(smallerDF1)

            println("TS2: value at this postcode")
            val smallerDF2 = dfTimeseries2.getTSatThisPostCode("postal_CH9050")
            println(smallerDF2)
        }
    }
}
```
```
I/System.out: first time series:
I/System.out:           lat        lon           validdate t_2m:C t_2m:K t_2m:F
I/System.out:   0 51.507322  -0.127647 2023-03-29T09:27:06   11.8  285.0   53.3
I/System.out:   1 51.507322  -0.127647 2023-03-29T12:27:06   13.8  286.9   56.8
I/System.out:   2 51.507322  -0.127647 2023-03-29T15:27:06   14.8  287.9   58.6
I/System.out:   3 51.507322  -0.127647 2023-03-29T18:27:06   13.0  286.1   55.3
I/System.out:   4 51.507322  -0.127647 2023-03-29T21:27:06   12.1  285.2   53.7
I/System.out:   5 51.507322  -0.127647 2023-03-30T00:27:06   11.7  284.8   53.0
I/System.out:   6 51.507322  -0.127647 2023-03-30T03:27:06   10.9  284.1   51.6
I/System.out:   7 51.507322  -0.127647 2023-03-30T06:27:06   10.9  284.1   51.7
I/System.out:   8 51.507322  -0.127647 2023-03-30T09:27:06   12.8  285.9   55.0
I/System.out:   9 35.395413 -97.595988 2023-03-29T09:27:06    2.1  275.3   35.9
I/System.out:  10 35.395413 -97.595988 2023-03-29T12:27:06    2.8  275.9   37.0
I/System.out:  11 35.395413 -97.595988 2023-03-29T15:27:06   10.7  283.8   51.2
I/System.out:  12 35.395413 -97.595988 2023-03-29T18:27:06   16.1  289.3   61.0
I/System.out:  13 35.395413 -97.595988 2023-03-29T21:27:06   17.9  291.0   64.2
I/System.out:  14 35.395413 -97.595988 2023-03-30T00:27:06   14.9  288.1   58.8
I/System.out:  15 35.395413 -97.595988 2023-03-30T03:27:06   11.4  284.6   52.6
I/System.out:  16 35.395413 -97.595988 2023-03-30T06:27:06    9.9  283.0   49.7
I/System.out:  17 35.395413 -97.595988 2023-03-30T09:27:06    9.6  282.7   49.2



I/System.out: second time series:
I/System.out:       postal_code           validdate t_2m:C t_2m:K t_2m:F
I/System.out:   0 postal_CH9000 2023-03-29T09:27:06   11.0  284.1   51.7
I/System.out:   1 postal_CH9000 2023-03-29T10:27:06   11.2  284.4   52.2
I/System.out:   2 postal_CH9000 2023-03-29T11:27:06   12.0  285.1   53.5
I/System.out:   3 postal_CH9000 2023-03-29T12:27:06   11.8  284.9   53.2
I/System.out:   4 postal_CH9000 2023-03-29T13:27:06   11.7  284.9   53.1
I/System.out:   5 postal_CH9000 2023-03-29T14:27:06   12.9  286.1   55.3
I/System.out:   6 postal_CH9000 2023-03-29T15:27:06   13.8  286.9   56.8
I/System.out:   7 postal_CH9000 2023-03-29T16:27:06   14.0  287.1   57.2
I/System.out:   8 postal_CH9000 2023-03-29T17:27:06   13.3  286.5   56.0
I/System.out:   9 postal_CH9000 2023-03-29T18:27:06   13.2  286.4   55.8
I/System.out:  10 postal_CH9000 2023-03-29T19:27:06   13.0  286.1   55.4
I/System.out:  11 postal_CH9000 2023-03-29T20:27:06   13.1  286.3   55.6
I/System.out:  12 postal_CH9000 2023-03-29T21:27:06   13.1  286.2   55.5
I/System.out:  13 postal_CH9000 2023-03-29T22:27:06   13.1  286.3   55.6
I/System.out:  14 postal_CH9000 2023-03-29T23:27:06   14.1  287.2   57.4
I/System.out:  15 postal_CH9000 2023-03-30T00:27:06   13.9  287.1   57.0
I/System.out:  16 postal_CH9000 2023-03-30T01:27:06   12.9  286.1   55.3
I/System.out:  17 postal_CH9000 2023-03-30T02:27:06   11.8  285.0   53.2
I/System.out:  18 postal_CH9000 2023-03-30T03:27:06   11.5  284.6   52.6
I/System.out:  19 postal_CH9000 2023-03-30T04:27:06   10.8  283.9   51.4


I/System.out: TS1: value at this lat and lon
I/System.out:          lat       lon           validdate t_2m:C t_2m:K t_2m:F
I/System.out:  0 51.507322 -0.127647 2023-03-29T09:33:46   11.9  285.0   53.4
I/System.out:  1 51.507322 -0.127647 2023-03-29T12:33:46   14.0  287.2   57.2
I/System.out:  2 51.507322 -0.127647 2023-03-29T15:33:46   14.7  287.8   58.4
I/System.out:  3 51.507322 -0.127647 2023-03-29T18:33:46   12.9  286.1   55.3


I/System.out: TS2: value at this postcode
I/System.out:       postal_code           validdate t_2m:C t_2m:K t_2m:F
I/System.out:   0 postal_CH9050 2023-03-29T09:33:46   10.3  283.5   50.6
I/System.out:   1 postal_CH9050 2023-03-29T10:33:46   11.0  284.2   51.8
I/System.out:   2 postal_CH9050 2023-03-29T11:33:46   11.1  284.2   51.9
I/System.out:   3 postal_CH9050 2023-03-29T12:33:46   11.3  284.5   52.4
I/System.out:   4 postal_CH9050 2023-03-29T13:33:46   10.7  283.9   51.3

```

For a start, we recommend to read the Examples.kt file, where additional 
examples on how to use each function in the Kotlin connector are provided.
