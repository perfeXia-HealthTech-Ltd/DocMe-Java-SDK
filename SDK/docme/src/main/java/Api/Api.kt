package Api

import Data.Measurement
import Data.Patient
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path


interface Api {
    @GET("/patient/{patientId}")
    fun getPatient(@Path("patientId") patientId: String): Call<Patient>

    @POST("/patient")
    fun newPatient(): Call<Patient>

    @GET("/patient/{patientId}/measurement/{measurementId}")
    fun getMeasurement(@Path("patientId") patientId: String,
                       @Path("measurementId") measurementId: String)
                       : Call<Measurement>


}