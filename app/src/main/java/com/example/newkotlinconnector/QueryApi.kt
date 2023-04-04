package com.example.newkotlinconnector

import okhttp3.Credentials
import retrofit2.HttpException
import java.io.IOException

class QueryApi {

    suspend fun queryApi(
        URL: String,
        BODY: String,
        optionalParams: List<String?>,
        username: String,
        password: String,
        requestType: String
    ): Parameters {
        val headers = emptyMap<String, String>().toMutableMap()
        headers["Authorization"] = Credentials.basic(username, password)
        lateinit var response: Parameters
        if (requestType == "GET") {
            response = try {
                RetrofitInstance.api.getRequest(
                    headers,
                    URL + BODY,
                    optionalParams[0],
                    optionalParams[1],
                    optionalParams[2],
                    optionalParams[3],
                    optionalParams[4]
                ).body()
            } catch (e: IOException) {
            } catch (e: HttpException) {
            } as Parameters
        } else {
            var parametersList: List<String> = emptyList<String>().toMutableList()
            if (optionalParams[0] != null) {
                parametersList += "model=${optionalParams[0]}"
            }
            if (optionalParams[1] != null) {
                parametersList += "interp_select=${optionalParams[1]}"
            }
            if (optionalParams[2] != null) {
                parametersList += "onInvalid=${optionalParams[2]}"
            }
            if (optionalParams[3] != null) {
                parametersList += "cluster_select=${optionalParams[3]}"
            }
            if (optionalParams[4] != null) {
                parametersList += "ens_select=${optionalParams[4]}"
            }

            response = RetrofitInstance.api.postRequest(
                headers,
                URL,
                BODY + parametersList.joinToString { "&" }
            ).body() as Parameters
        }
        return response
    }
}