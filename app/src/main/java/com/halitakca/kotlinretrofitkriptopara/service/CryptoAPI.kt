package com.halitakca.kotlinretrofitkriptopara.service

import retrofit2.http.GET
import com.halitakca.kotlinretrofitkriptopara.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call

interface CryptoAPI {

    //GET, POST, UPDATE, DELETE

    // https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    //fun getData(): Call<List<CryptoModel>>

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Observable<List<CryptoModel>>

}


