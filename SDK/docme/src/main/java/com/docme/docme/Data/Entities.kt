package com.docme.docme.Api.Data

import com.docme.docme.Api.Api
import com.docme.docme.Interactor.DocMeServerException
import com.docme.docme.Interactor.Interactor
import com.docme.docme.Interactor.createRetrofitApi
import retrofit2.Call
import java.io.File

data class Patient(var id: String = "") {
    fun newMeasurement(measurementTimestamp: Long, video: File): Measurement {
        return interactor.newMeasurement(patientId = id, measurementTimestamp, video)
    }

    fun newMeasurement(measurementTimestamp: Long, videoPath: String): Measurement {
        return interactor.newMeasurement(patientId = id, measurementTimestamp, videoPath)
    }

    fun getMeasurement(measurementId: String): Measurement {
        return interactor.getMeasurement(id, measurementId)
    }

    fun getHM3(): Conclusion {
        return interactor.getHM3ForPatient(id)
    }

    fun deletePatient() {
        interactor.deletePatient(id)
    }


    companion object {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)

        fun newPatient(): Patient {
            val callPatient: Call<Patient> = api.newPatient()
            val serverResponse = callPatient.execute()
            if (serverResponse.raw().code() == 200){
                return serverResponse.body()!!
            } else {
                throw  DocMeServerException(serverResponse.raw().message())
            }
        }

        fun getPatient(patientId: String): Patient {
            val callPatient: Call<Patient> = api.getPatient(patientId)
            val serverResponse = callPatient.execute()
            if (serverResponse.raw().code() == 200){
                return serverResponse.body()!!
            } else {
                throw DocMeServerException(serverResponse.raw().message())
            }
        }

    }
}

data class Measurement(
    var id: String = "",
    var patientId: String = "",
    var status: State = State.NOT_INITIALIZED,
    var timestamp: Long = -1,
    var details: String = "",
    var errorDetails: String = ""
) {
    companion object {
        enum class State(val state: String) {
            NOT_INITIALIZED("NOT_INITIALIZED"),
            INITIALIZED("INITIALIZED"),
            PROCESSING("PROCESSING"),
            SUCCESS("SUCCESS"),
            ERROR("ERROR")
        }
    }
}

data class Conclusion(
    var id: String = "",
    var state: String = "",
    var message: String = ""
)