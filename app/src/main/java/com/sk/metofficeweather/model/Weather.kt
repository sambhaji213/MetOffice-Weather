package com.sk.metofficeweather.model

/*
 * Created by Sambhaji Karad on 08-03-2019
*/

data class Weather(
    var id: Int? = null,
    var rainFall: String? = null,
    var tmax: String? = null,
    var tmin: String? = null,
    var value: String? = null,
    var year: Int? = null,
    var month: Int? = null
)