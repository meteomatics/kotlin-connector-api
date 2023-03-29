package com.example.newkotlinconnector

class Constants {
    // Collect all constant definitions here
    val DEFAULT_API_BASE_URL = "https://api.meteomatics.com"

    val nA_VALUES = arrayOf(-666, -777, -888, -999) // in python is tuple, in my code array

    // templates
    val TIME_SERIES_TEMPLATE =
        "{api_base_url}/{startdate}--{enddate}:{interval}/{parameters}/{coordinates}/bin?{urlParams}"
    val GRID_TEMPLATE = "{api_base_url}/{startdate}/{parameter_grid}/{lat_N},{lon_W}_{lat_S},{lon_E}:{res_lat},{res_lon}/bin?{urlParams}"
    val GRID_TIME_SERIES_TEMPLATE = "{api_base_url}/{startdate}--{enddate}:{interval}/{parameters}/{lat_N},{lon_W}_{lat_S},{lon_E}:{res_lat},{res_lon}/bin?{urlParams}"
    val POLYGON_TEMPLATE = "{api_base_url}/{startdate}--{enddate}:{interval}/{parameters}/{coordinates_aggregation}/csv?{urlParams}"
    val GRID_PNG_TEMPLATE = "{api_base_url}/{startdate}/{parameter_grid}/{lat_N},{lon_W}_{lat_S},{lon_E}:{res_lat},{res_lon}/png?{urlParams}"
    val LIGHTNING_TEMPLATE = "{api_base_url}/get_lightning_list?time_range={startdate}--{enddate}&bounding_box={lat_N},{lon_W}_{lat_S},{lon_E}&source={source}&format=csv"
    val NETCDF_TEMPLATE = "{api_base_url}/{startdate}--{enddate}:{interval}/{parameter_netcdf}/{lat_N},{lon_W}_{lat_S},{lon_E}:{res_lat},{res_lon}/netcdf?{urlParams}"
    val STATIONS_LIST_TEMPLATE = "{api_base_url}/find_station?{urlParams}"
    val INIT_DATE_TEMPLATE = "{api_base_url}/get_init_date?model={model}&valid_date={interval_string}&parameters={parameter}"
    val AVAILABLE_TIME_RANGES_TEMPLATE = "{api_base_url}/get_time_range?model={model}&parameters={parameters}"
    val logdepth = 0
}