package com.example.doktorrandevuapp.network

import com.example.doktorrandevuapp.data.Data
import retrofit2.Response
import retrofit2.http.GET

interface DoctorApi {

    @GET("doctors.json")
    suspend fun getDoctors(): Response<Data>

}