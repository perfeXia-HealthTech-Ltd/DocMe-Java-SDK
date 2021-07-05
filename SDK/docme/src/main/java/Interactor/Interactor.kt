package Interactor

import Api.Api
import Data.Patient


import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


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
    fun getPatient(patientId: String): Patient {
        val callPatient: Call<Patient> = apiService.getPatient(patientId)
        return callPatient.execute().body()!!
    }

    fun newPatient(): Patient {
        val callPatient: Call<Patient> = apiService.newPatient()
        return callPatient.execute().body()!!
    }
}