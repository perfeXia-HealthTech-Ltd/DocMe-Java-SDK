package com.driftique.docme

import Api.Api
import Data.Measurement
import Data.Patient
import Data.State
import Interactor.Interactor
import Interactor.createRetrofitApi

import org.junit.Test

import org.junit.Assert.*

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

        assertArrayEquals(arrayOf(Bob, Masha, Misha), arrayOf(sameBob, sameMasha, sameMisha))
    }

    @Test
    fun simpleGetMeasurment() {
        val patientId: String = "5b9e968c-667c-4160-89d4-1abea68e84c3"
        val measurementId: String = "ce81f59d-2375-4531-85fd-e1f4fe247b10"

        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)

        val requested = Measurement(id = "ce81f59d-2375-4531-85fd-e1f4fe247b10",
                                                 status = State.SUCCESS,
                                                 timestamp = 1624224079)
        val measurementForPatient: Measurement = interactor.getMeasurement(patientId, measurementId)

        assertEquals(requested, measurementForPatient)
    }


}