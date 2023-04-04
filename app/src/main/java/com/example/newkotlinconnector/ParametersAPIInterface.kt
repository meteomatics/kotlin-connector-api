package com.example.newkotlinconnector

import retrofit2.Response
import retrofit2.http.*

interface ParametersAPIInterface {
    @GET
    suspend fun getRequest(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Query("model") model: String?,
        @Query("interp_select") interp_select: String?,
        @Query("on_invalid") on_invalid: String?,
        @Query("cluster_select") cluster_select: String?,
        @Query("ens_select") ens_select: String?
    ): Response<Parameters>

    @POST
    suspend fun postRequest(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Body body: String,
    ): Response<Parameters>

}