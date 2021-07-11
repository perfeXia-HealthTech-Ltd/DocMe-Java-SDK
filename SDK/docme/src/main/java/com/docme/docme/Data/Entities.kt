package com.docme.docme.Api.Data

import com.docme.docme.Api.Api
import com.docme.docme.Interactor.DocMeServerException
import com.docme.docme.Interactor.Interactor
import com.docme.docme.Interactor.createRetrofitApi
import retrofit2.Call
import java.io.File


/**
 * Entity "Patient"
 */
data class Patient(var id: String = "") {
    /**
     * Method od adding a new measurement
     * @param measurementTimestamp time when the measurement was taken
     * @param video file with video
     * @return new measurement with params
     */
    fun newMeasurement(measurementTimestamp: Long, video: File): Measurement {
        return interactor.newMeasurement(patientId = id, measurementTimestamp, video)
    }
    /**
     * Another method od adding a new measurement
     * @param measurementTimestamp time when the measurement was taken
     * @param videoPath path to video
     * @return new measurement with params
     */
    fun newMeasurement(measurementTimestamp: Long, videoPath: String): Measurement {
        return interactor.newMeasurement(patientId = id, measurementTimestamp, videoPath)
    }

    /**
     * Method of getting measurement by id
     * @param measurementId id of a measurement you want
     * @return measurement with the desired id
     */
    fun getMeasurement(measurementId: String): Measurement {
        return interactor.getMeasurement(id, measurementId)
    }

    /**
     * Method of getting HM3 for patient
     * @return conclusion for patient
     */
    fun getHM3(): Conclusion {
        return interactor.getHM3ForPatient(id)
    }

    /**
     * Method of deleting a patient
     */
    fun deletePatient() {
        interactor.deletePatient(id)
    }


    companion object {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)


        /**
         * Creating a new patient
         * @throws DocMeServerException if smth wrong with server
         * @return new Patient
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
         * Getting patient by id
         * @param patientId id of a patient
         * @throws DocMeServerException if smth wrong with server
         * @return patient by id
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
         * enum of states, which measurement can have
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