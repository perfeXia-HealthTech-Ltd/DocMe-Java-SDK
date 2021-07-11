package com.docme.docme.Interactor

import android.media.MediaPlayer
import com.docme.docme.Api.Api
import com.docme.docme.Api.Data.Conclusion
import com.docme.docme.Api.Data.Measurement
import com.google.gson.Gson
import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream


val BASE_URL = "https://docme1.p.rapidapi.com"
val KEY = "383efded5bmsh5a4cdd9353ec742p176864jsnfcbb6e3aeda0"

val MIN_TIME_LIMIT = 10
val MAX_TIME_LIMIT = 20

/**
 * Creating retrofit object
 */
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

/**
 * Function that determines the duration of the video along the path to file
 */
fun timeInSeconds(pathToFile: String): Int{
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setDataSource(pathToFile)
    mediaPlayer.prepare()
    val time: Int = mediaPlayer.duration
    return time / 1000
}


/**
 * Creating an interactor object to interact with api
 */
class Interactor(val apiService: Api) {

    val gson = Gson()

    val MEDIA_TYPE_MP4 = MediaType.parse("video/mp4")
    val MEDIA_TYPE_MOV = MediaType.parse("video/quicktime")

    data class TestModel(val id       : String,
                         val status   : Measurement.Companion.State,
                         val timestamp: Long)

    private fun makeUploadWithFormat(mediaType: MediaType,
                                     client: OkHttpClient,
                                     patientId: String,
                                     video: File,
                                     videoName: String,
                                     measurementTimestamp: Long): Measurement {

        val duration = timeInSeconds(video.path)
        if (duration < MIN_TIME_LIMIT || duration > MAX_TIME_LIMIT) {
           throw Exception("not appropriate duration of a videoFile")
        }

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("video", videoName, RequestBody.create(mediaType, FileInputStream(video).readBytes()))
            .addFormDataPart("timestamp", measurementTimestamp.toString())
            .build()

        val request = Request.Builder()
            .url(BASE_URL + "/patient/" + patientId + "/measurement")
            .addHeader("X-RapidAPI-Key", KEY)
            .post(requestBody).build()


        val serverResponse = client.newCall(request).execute()
        if (serverResponse.code() == 200){
            var testModel = gson.fromJson(serverResponse.body()!!.string(), TestModel::class.java)
            return Measurement(id = testModel.id, status = testModel.status, timestamp = testModel.timestamp)
        } else {
            throw  DocMeServerException(serverResponse.message())
        }

    }

    private fun uploadVideo(client: OkHttpClient, patientId: String, video: File, videoName: String, measurementTimestamp: Long): Measurement {
        val measurement: Measurement

        if (videoName.substringAfterLast('.') == "mp4") {
            measurement = makeUploadWithFormat(MEDIA_TYPE_MP4!!, client, patientId, video, videoName, measurementTimestamp)
        } else if (videoName.substringAfterLast('.') == "mov") {
            measurement = makeUploadWithFormat(MEDIA_TYPE_MOV!!, client, patientId, video, videoName, measurementTimestamp)
        } else {
            throw Exception("not avaliable format")
        }

       return measurement
    }

    /**
     * Function that deletes patient by id
     * @param patientId id of the patient to delete
     * @throws DocMeServerException if smth goes wrong with server
     */
    fun deletePatient(patientId: String) {
        val deletedPatient: Call<Unit> = apiService.deletePatient(patientId)
        val serverResponse = deletedPatient.execute()
        if (serverResponse.raw().code() != 200){
            throw  DocMeServerException(serverResponse.raw().message())
        }
    }
    /**
     * Function that gets measurement by patient and measurement ids
     * @param patientId id of the patient
     * @param measurementId id of the measurement
     * @throws DocMeServerException if smth goes wrong with server
     * @return measurement
     */
    fun getMeasurement(patientId: String, measurementId: String): Measurement {
        val callMeasurement: Call<Measurement> = apiService.getMeasurement(patientId, measurementId)
        val serverResponse = callMeasurement.execute()
        if (serverResponse.raw().code() == 200){
            return serverResponse.body()!!
        } else {
            throw DocMeServerException(serverResponse.raw().message())
        }
    }
    /**
     * Function that creates new measurement by patient id, measurement time stamp and video file
     * @param patientId id of the patient
     * @param measurementTimestamp time stamp of the measurement
     * @param video video file
     * @return new measurement
     */
    fun newMeasurement(patientId: String, measurementTimestamp: Long, video: File): Measurement {
        val client = OkHttpClient()
        val videoName: String = video.name
        return uploadVideo(client, patientId, video, videoName, measurementTimestamp)
    }
    /**
     * Overloaded function that creates new measurement by patient id, measurement time stamp and video path
     * @param patientId id of the patient
     * @param measurementTimestamp time stamp of the measurement
     * @param video video path
     * @return new measurement
     */
    fun newMeasurement(patientId: String, measurementTimestamp: Long, videoPath: String): Measurement {
        val client = OkHttpClient()
        val videoName: String = videoPath.substringAfterLast('/')
        return uploadVideo(client, patientId, File(videoPath), videoName, measurementTimestamp)
    }
    /**
     * Function that gets conclusion for patient
     * @param patientId id of the patient to get conclusion
     * @throws DocMeServerException if smth goes wrong with server
     * @return conclusion
     */
    fun getHM3ForPatient(patientId: String): Conclusion {
        val callConclusion: Call<Conclusion> = apiService.getHM3ForPatient(patientId)
        val serverResponse = callConclusion.execute()
        if (serverResponse.raw().code() == 200){
            return serverResponse.body()!!
        } else {
            throw  DocMeServerException(serverResponse.raw().message())
        }
    }

}

