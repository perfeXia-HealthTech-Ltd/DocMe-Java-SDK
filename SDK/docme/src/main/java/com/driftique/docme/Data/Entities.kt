package com.driftique.docme.Api.Data

import com.driftique.docme.Api.Api
import com.driftique.docme.Interactor.Interactor
import com.driftique.docme.Interactor.UploadService
import com.driftique.docme.Interactor.createRetrofitApi
import okhttp3.OkHttpClient
import java.io.File

enum class State(val state: String) {
    NOT_INITIALIZED("NOT_INITIALIZED"),
    INITIALIZED("INITIALIZED"),
    PROCESSING("PROCESSING"),
    SUCCESS("SUCCESS"),
    ERROR("ERROR")
}

class Patient(var id: String = "NO ID YET") {
    fun newMeasurement(measurementTimestamp: Long, video: File, videoName: String): Measurement {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        return interactor.newMeasurement(patientId = id, measurementTimestamp, video, videoName)
    }

    fun getMeasurement(measurementId: String): Measurement {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        return interactor.getMeasurement(id, measurementId)
    }

    fun getHM3(): Conclusion {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        return interactor.getHM3ForPatient(id)
    }
}

data class Measurement(
    var id: String = "NO MEASUREMENT ID YET",
    var patientId: String = "NO PATIENT ID YET",
    var status: State = State.NOT_INITIALIZED,
    var timestamp: Long = -1,
    var details: Any = "No details now",
    //var errorDetails: Error
)

data class Conclusion(
    var id: String = "NO ID YET",
    var state: String = "NO STATE YET",
    var message: String = "NO MESSAGE"
)