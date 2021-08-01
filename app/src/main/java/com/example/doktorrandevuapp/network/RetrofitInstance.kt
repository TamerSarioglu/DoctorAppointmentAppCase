package com.example.doktorrandevuapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: DoctorApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.mobillium.com/android/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DoctorApi::class.java)
    }

}