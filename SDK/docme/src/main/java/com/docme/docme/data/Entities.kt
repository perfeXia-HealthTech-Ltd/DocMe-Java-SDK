package com.docme.docme.data

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import com.docme.docme.api.Api
import com.docme.docme.interactor.DocMeServerException
import com.docme.docme.interactor.Interactor
import com.docme.docme.interactor.createRetrofitApi
import retrofit2.Call
import java.io.File


/**
 * Entity "Patient"
 */
data class Patient(val id: String = "") {
    /**
     * Method of adding a new [Measurement]
     * @param measurementTimestamp time when the measurement was taken
     * @param video File with video
     * @return new [Measurement] with params
     */
    fun newMeasurement(measurementTimestamp: Long, video: File): Measurement {
        return interactor.newMeasurement(patientId = id, measurementTimestamp, video)
    }
    /**
     * Another method of adding a new [Measurement]
     * @param measurementTimestamp time when the measurement was taken
     * @param videoPath path to video
     * @return new [Measurement] with params
     */
    fun newMeasurement(measurementTimestamp: Long, videoPath: String): Measurement {
        return interactor.newMeasurement(patientId = id, measurementTimestamp, videoPath)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun newMeasurement(this_: Context, measurementTimestamp: Long, uri: Uri): Measurement {
        return interactor.newMeasurement(this_, patientId = id, measurementTimestamp, uri)
    }

    /**
     * Method of getting [Measurement] by id
     * @param measurementId id of a measurement you want
     * @return [Measurement] with the desired id
     */
    fun getMeasurement(measurementId: String): Measurement {
        return interactor.getMeasurement(id, measurementId)
    }

    /**
     * Method for getting HM3 for [Patient]
     * @return conclusion for patient
     */
    fun getHM3(): Conclusion {
        return interactor.getHM3ForPatient(id)
    }

    /**
     * Method of deleting a [Patient]
     */
    fun deletePatient() {
        interactor.deletePatient(id)
    }


    companion object {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)


        /**
         * Creating a new [Patient]
         */
        fun newPatient(): Patient {
            return interactor.newPatient()
        }

        /**
         * Getting [Patient] by id
         */
        fun getPatient(patientId: String): Patient {
            return interactor.getPatient(patientId)
        }

    }
}


/**
 * Entity "Measurement"
 */
data class Measurement(
    val id: String = "",
    val patientId: String = "",
    var status: State = State.NOT_INITIALIZED,
    val timestamp: Long = -1,
    var details: String = "",
    var errorDetails: String = ""
) {
    companion object {
        /**
         * enum of states, which [Measurement] can have
         */
        enum class State(val state: String) {
            NOT_INITIALIZED("NOT_INITIALIZED"),
            INITIALIZED("INITIALIZED"),
            PROCESSING("PROCESSING"),
            SUCCESS("SUCCESS"),
            ERROR("ERROR")
        }
    }
}

/**
 * Entity "Conclusion"
 */
data class Conclusion(
    val id: String = "",
    var state: String = "",
    var message: String = ""
)