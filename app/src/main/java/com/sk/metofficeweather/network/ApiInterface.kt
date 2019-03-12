package com.sk.metofficeweather.network

import com.sk.metofficeweather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/*
 * Created by Sambhaji Karad on 08-3-2019
*/

interface ApiInterface {

    @GET("metoffice/{metric}-{location}.json")
    fun getMetOfficeData(
        @Path(value = "metric") metric: String,
        @Path(value = "location") location: String
    ): Call<ArrayList<Weather>>
}
