package com.example.newkotlinconnector


class BuildYourUrl {
    fun buildUrlTimeSeries(
        startdate: String,
        enddate: String,
        interval: String, //:PT5M for 5 minutes
        parameters: List<String>,
        coordinateList: List<String>
    ): List<String> {
        return listOf("/$startdate--$enddate:$interval/", "${parameters.joinToString(",")}/${coordinateList.joinToString("+")}/json?")
    }

    fun buildUrlGrid(
    startdate: String,
    parameter: String,
    coordinateList: List<String>,
    resolutionList: List<String>
    ): List<String> {
        return listOf("/$startdate/", "${parameter}/${coordinateList.joinToString("_")}:${resolutionList.joinToString(",")}/json?")
        //val GRID_TEMPLATE = "{api_base_url}/{startdate}/{parameter_grid}/{lat_N},{lon_W}_{lat_S},{lon_E}:{res_lat},{res_lon}/bin?{urlParams}"
    }

    fun buildUrlGridTimeSeries(
        startdate: String,
        enddate: String,
        interval: String, //:PT5M for 5 minutes
        parameters: List<String>,
        coordinateList: List<String>,
        resolutionList: List<String>
    ): List<String> {
        return listOf("/$startdate--$enddate:$interval/", "${parameters.joinToString(",")}/${coordinateList.joinToString("_")}:${resolutionList.joinToString(",")}/json?")
    }
    //"{api_base_url}/{startdate}--{enddate}:{interval}/{parameters}/{lat_N},{lon_W}_{lat_S},{lon_E}:{res_lat},{res_lon}/bin?{urlParams}"
}