package com.driftique.docme.Interactor


import com.driftique.docme.Api.Api
import com.driftique.docme.Api.Tmp
import com.driftique.docme.Api.Data.Conclusion
import com.driftique.docme.Api.Data.Measurement
import com.driftique.docme.Api.Data.Patient
import com.driftique.docme.Api.Data.State
import com.google.gson.Gson
import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException


val BASE_URL = "https://docme1.p.rapidapi.com"
val KEY = "383efded5bmsh5a4cdd9353ec742p176864jsnfcbb6e3aeda0"

fun createRetrofitApi(): Api {

    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor { chain ->
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("x-rapidapi-key", KEY)
            .build()
        chain.proceed(request)
    }

    var retrofit_client: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()
    return retrofit_client.create(Api::class.java)
}



class Interactor(val apiService: Api) {

    val gson = Gson()

    val MEDIA_TYPE_MP4 = MediaType.parse("video/mp4")

    data class TestModel(val id       : String,
                         val status   : State,
                         val timestamp: Long)

    fun uploadVideo(client: OkHttpClient, patientId: String, video: File, videoName: String, measurementTimestamp: Long): Measurement {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("video", videoName, RequestBody.create(MEDIA_TYPE_MP4,FileInputStream(video).readBytes() /*video*/))
            .addFormDataPart("timestamp", measurementTimestamp.toString())
            .build()

        val request = Request.Builder()
            .url(BASE_URL + "/patient/" + patientId + "/measurement")
            .addHeader("X-RapidAPI-Key", KEY)
            .post(requestBody).build()


        var testModel = gson.fromJson(client.newCall(request).execute().body()!!.string(), TestModel::class.java)

        return Measurement(id = testModel.id, status = testModel.status, timestamp = testModel.timestamp)
    }

    fun getPatient(patientId: String): Patient {
        val callPatient: Call<Patient> = apiService.getPatient(patientId)
        return callPatient.execute().body()!!
    }

    fun newPatient(): Patient {
        val callPatient: Call<Patient> = apiService.newPatient()
        return callPatient.execute().body()!!
    }

    fun getMeasurement(patientId: String, measurementId: String): Measurement {
        val callMeasurement: Call<Measurement> = apiService.getMeasurement(patientId, measurementId)
        return callMeasurement.execute().body()!!
    }

    fun newMeasurement(patientId: String, measurementTimestamp: Long/*, video: ByteArray*/, video: File, videoName: String): Measurement {
        val client = OkHttpClient()
        return uploadVideo(client, patientId, video, videoName, measurementTimestamp)
    }

    fun getHM3ForPatient(patientId: String): Conclusion {
        val callConclusion: Call<Conclusion> = apiService.getHM3ForPatient(patientId)
        return callConclusion.execute().body()!!
    }

}

