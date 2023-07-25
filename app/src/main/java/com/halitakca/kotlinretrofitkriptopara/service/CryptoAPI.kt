package com.halitakca.kotlinretrofitkriptopara.service

import retrofit2.http.GET
import com.halitakca.kotlinretrofitkriptopara.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call

interface CryptoAPI {

    //GET, POST, UPDATE, DELETE

    //https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    //https://api.nomics.com/v1/
    // prices?key=2187154b76945f2373394aa34f7dc98a

    //@GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //@GET("prices?key=2187154b76945f2373394aa34f7dc98a")
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Call<List<CryptoModel>>

    //fun getData(): Observable<List<CryptoModel>>

}


