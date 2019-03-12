package com.sk.metofficeweather.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Created by Sambhaji Karad on 08-03-2019
*/

object ApiClient {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://s3.eu-west-2.amazonaws.com/"
    private val BASE_LOCATION = "interview-question-data/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL + BASE_LOCATION)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }
}