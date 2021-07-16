package com.docme.docme.api

import com.docme.docme.data.Conclusion
import com.docme.docme.data.Measurement
import com.docme.docme.data.Patient
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface of the API
 */
interface Api {
    @GET("/patient/{patientId}")
    fun getPatient(@Path("patientId") patientId: String): Call<Patient>

    @DELETE("/patient/{patientId}")
    fun deletePatient(@Path("patientId") patientId: String): Call<Unit>

    @POST("/patient")
    fun newPatient(): Call<Patient>

    @GET("/patient/{patientId}/measurement/{measurementId}")
    fun getMeasurement(@Path("patientId") patientId: String,
                       @Path("measurementId") measurementId: String)
                       : Call<Measurement>

    @GET("/patient/{patientId}/hm3")
    fun getHM3ForPatient(@Path("patientId") patientId: String): Call<Conclusion>
}