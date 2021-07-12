package com.docme.docme.data

import com.docme.docme.api.Api
import com.docme.docme.interactor.DocMeServerException
import com.docme.docme.interactor.Interactor
import com.docme.docme.interactor.createRetrofitApi
import retrofit2.Call
import java.io.File


/**
 * Entity "Patient"
 */
data class Patient(var id: String = "") {
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
         * @throws [DocMeServerException] if something goes wrong with server
         * @return new [Patient]
         */
        fun newPatient(): Patient {
            val callPatient: Call<Patient> = api.newPatient()
            val serverResponse = callPatient.execute()
            if (serverResponse.raw().code() == 200){
                return serverResponse.body()!!
            } else {
                throw  DocMeServerException(serverResponse.raw().message())
            }
        }

        /**
         * Getting [Patient] by id
         * @param patientId of a patient
         * @throws [DocMeServerException] if something goes wrong with server
         * @return [Patient]
         */
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


/**
 * Entity "Measurement"
 */
data class Measurement(
    var id: String = "",
    var patientId: String = "",
    var status: State = State.NOT_INITIALIZED,
    var timestamp: Long = -1,
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
    var id: String = "",
    var state: String = "",
    var message: String = ""
)