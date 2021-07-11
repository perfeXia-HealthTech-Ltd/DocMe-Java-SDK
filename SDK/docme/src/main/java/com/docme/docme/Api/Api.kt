package com.docme.docme.Api

import com.docme.docme.Api.Data.Conclusion
import com.docme.docme.Api.Data.Measurement
import com.docme.docme.Api.Data.Patient
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface of the api
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