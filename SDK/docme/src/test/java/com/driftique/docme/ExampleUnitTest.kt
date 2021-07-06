package com.driftique.docme

import android.os.Environment
import com.driftique.docme.Api.Api
import com.driftique.docme.Api.Data.Conclusion
import com.driftique.docme.Api.Data.Measurement
import com.driftique.docme.Api.Data.Patient
import com.driftique.docme.Api.Data.State
import com.driftique.docme.Interactor.BASE_URL
import com.driftique.docme.Interactor.Interactor
import com.driftique.docme.Interactor.createRetrofitApi
import okhttp3.*
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.IOException


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun idIsCorrect() {
        val id = "5b9e968c-667c-4160-89d4-1abea68e84c3"
        val api: Api = createRetrofitApi()
        val interactor: Interactor =  Interactor(api)
        val sameKot: Patient = interactor.getPatient(id)
        assertEquals(sameKot.id, id)

    }

    @Test
    fun sameResult() {
        val api: Api = createRetrofitApi()
        val interactor: Interactor =  Interactor(api)

        val Bob: Patient = interactor.newPatient()
        val Masha: Patient = interactor.newPatient()
        val Misha: Patient = interactor.newPatient()

        val sameBob: Patient = interactor.getPatient(Bob.id)
        val sameMasha: Patient = interactor.getPatient(Masha.id)
        val sameMisha: Patient = interactor.getPatient(Misha.id)

        assertArrayEquals(arrayOf(Bob.id, Masha.id, Misha.id), arrayOf(sameBob.id, sameMasha.id, sameMisha.id))
    }

    @Test
    fun simpleGetMeasurment() {
        val patientId: String = "5b9e968c-667c-4160-89d4-1abea68e84c3"
        val measurementId: String = "ce81f59d-2375-4531-85fd-e1f4fe247b10"

        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)

        val requested = Measurement(id        = "ce81f59d-2375-4531-85fd-e1f4fe247b10",
                                    status    = State.SUCCESS,
                                    timestamp = 1624224079)
        val measurementForPatient: Measurement = interactor.getMeasurement(patientId, measurementId)

        assertEquals(requested, measurementForPatient)
    }

    @Test
    fun getConclusion() {
        val patientId: String = "5b9e968c-667c-4160-89d4-1abea68e84c3"

        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val requested = Conclusion(state   = "HealthyState",
                                   message = "Please continue to check your blood pressure are " +
                                             "least every 5 years, or annually if systolic 135-139mmHg " +
                                             "or diastolic 85-89mmHg. You can read more about high blood" +
                                             " pressure here : https://patient.info/heart-health/high-blood-pressure-hypertension")

        val conclusion = interactor.getHM3ForPatient(patientId)
        assertEquals(requested, conclusion)
    }

    @Test
    fun newMeasurement() {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val Bob: Patient = interactor.newPatient()
        val measurementTimestamp: Long = 1624224079
        val result:  Measurement = Bob.newMeasurement(measurementTimestamp,
                                                      File("/home/alex-criwer/Downloads/Test/video.mp4"),
                                            "video.mp")

        val requested = Measurement(id        = result.id,
                                    status    = State.PROCESSING,
                                    timestamp = 1624224079)

        assertEquals(result, requested)
    }

    @Test
    fun gettingMeasurementFromPatient() {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val patient_id: String = "5b9e968c-667c-4160-89d4-1abea68e84c3"
        val measurement_id: String = "ce81f59d-2375-4531-85fd-e1f4fe247b10"
        val Bob: Patient = interactor.getPatient(patient_id)
        val measurement: Measurement = Bob.getMeasurement(measurement_id)
        val requested = Measurement(id        = "ce81f59d-2375-4531-85fd-e1f4fe247b10",
                                    status    = State.SUCCESS,
                                    timestamp = 1624224079)
        assertEquals(requested, measurement)
    }

    @Test
    fun gettingConclusionFromPatient() {
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val patient_id: String = "5b9e968c-667c-4160-89d4-1abea68e84c3"
        val Bob: Patient = interactor.getPatient(patient_id)
        val conclusion = Bob.getHM3()
        val requested = Conclusion(state   = "HealthyState",
                                   message = "Please continue to check your blood pressure are " +
                                             "least every 5 years, or annually if systolic 135-139mmHg " +
                                             "or diastolic 85-89mmHg. You can read more about high blood" +
                                             " pressure here : https://patient.info/heart-health/high-blood-pressure-hypertension")

        assertEquals(requested, conclusion)
    }



}