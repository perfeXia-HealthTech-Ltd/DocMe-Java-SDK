package com.docme.docme

import com.docme.docme.api.Api
import com.docme.docme.data.Conclusion
import com.docme.docme.data.Measurement
import com.docme.docme.data.Patient
import com.docme.docme.data.Patient.Companion.getPatient
import com.docme.docme.data.Patient.Companion.newPatient
import com.docme.docme.interactor.DocMe
import com.docme.docme.interactor.Interactor
import com.docme.docme.interactor.createRetrofitApi
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val KEY = "YOUR_DOCME_API_KEY"
    private val PATH_TO_VIDEO = "PATH_TO_YOUR_VIDEO"

    @Test
    fun idIsCorrect() {
        DocMe.initSDK(KEY)
        val Bob: Patient = newPatient()
        val sameBob: Patient = getPatient(Bob.id)
        assertEquals(sameBob.id, Bob.id)
    }

    @Test
    fun sameResult() {
        DocMe.initSDK(KEY)
        val Bob: Patient = newPatient()
        val Masha: Patient = newPatient()
        val Misha: Patient = newPatient()

        val sameBob: Patient = getPatient(Bob.id)
        val sameMasha: Patient = getPatient(Masha.id)
        val sameMisha: Patient = getPatient(Misha.id)

        assertArrayEquals(arrayOf(Bob.id, Masha.id, Misha.id),
                          arrayOf(sameBob.id, sameMasha.id, sameMisha.id))
    }

    @Test
    fun simpleGetMeasurment() {
        DocMe.initSDK(KEY)
        val measurementTimestamp: Long = 1624224079
        val pathToFile: String = "VIDEO_PATH_ON_YOUR_COMPUTER"

        val Bob: Patient = newPatient()
        val measurement:  Measurement = Bob.newMeasurement(measurementTimestamp, File(pathToFile))

        val measurementForPatient: Measurement = Bob.getMeasurement(measurement.id)

        assertEquals(arrayOf(measurement.id, measurement.timestamp),
                     arrayOf(measurementForPatient.id, measurementForPatient.timestamp))
    }

    @Test
    fun getConclusion() {
        DocMe.initSDK(KEY)
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val requested = Conclusion(state   = "HealthyState",
                                   message = "Please continue to check your blood pressure are " +
                                             "least every 5 years, or annually if systolic 135-139mmHg " +
                                             "or diastolic 85-89mmHg. You can read more about high blood" +
                                             " pressure here : https://patient.info/heart-health/high-blood-pressure-hypertension")

        val Bob: Patient = newPatient()
        val conclusion = Bob.getHM3()
        assertEquals(requested, conclusion)
    }

    @Test
    fun newMeasurementMP4Video() {
        DocMe.initSDK(KEY)
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val Bob: Patient = newPatient()
        val measurementTimestamp: Long = 1624224079
        val pathToFile = PATH_TO_VIDEO


        //val desc = FileInputStream(File(pathToFile)).fd
        val result:  Measurement = Bob.newMeasurement(measurementTimestamp,
                                                      File(pathToFile))

        val requested = Measurement(id        = result.id,
                                    status    = Measurement.Companion.State.PROCESSING,
                                    timestamp = 1624224079)


        assertEquals(arrayOf(requested.id, requested.status, requested.timestamp),
                     arrayOf(result.id, result.status, result.timestamp))
    }

    @Test
    fun newMeasurementMOVVideo() {
        DocMe.initSDK(KEY)
        val Bob: Patient = newPatient()
        val measurementTimestamp: Long = 1624224079
        val result:  Measurement = Bob.newMeasurement(measurementTimestamp,
            File(PATH_TO_VIDEO))

        val requested = Measurement(id        = result.id,
                                    status    = Measurement.Companion.State.PROCESSING,
                                    timestamp = 1624224079)

        assertEquals(arrayOf(requested.id, requested.status, requested.timestamp),
                     arrayOf(result.id, result.status, result.timestamp))
    }

    @Test
    fun gettingMeasurementFromPatient() {
        DocMe.initSDK(KEY)
        val measurementTimestamp: Long = 1624224079
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)

        val Bob: Patient = newPatient()
        val  measurement:  Measurement = Bob.newMeasurement(measurementTimestamp,
            File(PATH_TO_VIDEO))

        val requested = Bob.getMeasurement(measurement.id)
        assertEquals(arrayOf(requested.id, requested.status, requested.timestamp),
                     arrayOf(measurement.id, measurement.status, measurement.timestamp))
    }


    @Test
    fun pathToFile() {
        DocMe.initSDK(KEY)
        val Bob: Patient = newPatient()
        val measurementTimestamp: Long = 1624224079
        val result:  Measurement = Bob.newMeasurement(measurementTimestamp,
            PATH_TO_VIDEO)

        val requested = Measurement(id        = result.id,
                                    status    = Measurement.Companion.State.PROCESSING,
                                    timestamp = 1624224079)

        assertEquals(arrayOf(requested.id, requested.status, requested.timestamp),
            arrayOf(result.id, result.status, result.timestamp))
    }

    @Test
    fun gettingConclusionFromPatient() {
        DocMe.initSDK(KEY)
        val Bob: Patient = newPatient()
        val conclusion = Bob.getHM3()
        val requested = Conclusion(state   = "HealthyState",
                                   message = "Please continue to check your blood pressure are " +
                                             "least every 5 years, or annually if systolic 135-139mmHg " +
                                             "or diastolic 85-89mmHg. You can read more about high blood" +
                                             " pressure here : https://patient.info/heart-health/high-blood-pressure-hypertension")

        assertEquals(requested, conclusion)
    }

    @Test
    fun deletePatient() {
        DocMe.initSDK(KEY)
        val Bob: Patient = newPatient()
        Bob.deletePatient()
       // val sameBob = getPatient(Bob.id)

        // Not implemented in DocMe api yet
    }


}