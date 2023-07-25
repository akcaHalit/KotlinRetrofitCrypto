package com.halitakca.kotlinretrofitkriptopara.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halitakca.kotlinretrofitkriptopara.databinding.ActivityMainBinding
import com.halitakca.kotlinretrofitkriptopara.model.CryptoModel
import com.halitakca.kotlinretrofitkriptopara.service.CryptoAPI
import retrofit2.Callback
import retrofit2.Call
import com.halitakca.kotlinretrofitkriptopara.adapter.RecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener{
    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null

    private var compositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        compositeDisposable = CompositeDisposable()

        loadData()


    }

    private fun loadData() {


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)
        // Service'i de burada verdik direkt.


        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())   // Main Thread'e girmeden Arka Plan'da çalış.
            .observeOn(AndroidSchedulers.mainThread())  // Verileri Main Thread'de işleyeceğiz.
            .subscribe(this::handleResponse))






        /*
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        println("SERVİCE ÖNCESİ")
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()


         call.enqueue(object: Callback<List<CryptoModel>> {
            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<CryptoModel>>, response: Response<List<CryptoModel>>){

                if(response.isSuccessful){

                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(cryptoModels!!, this@MainActivity)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }
                        println("ADAPTER OKEY")


                        /*
                        for(cryptoModel : CryptoModel in cryptoModels!!){
                            println(cryptoModel.currency)
                            println(cryptoModel.price)
                        }

                         */
                    }
                }

            }
        })

         */


    }


    private fun handleResponse(cryptoList : List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            recyclerViewAdapter = RecyclerViewAdapter(cryptoModels!!, this@MainActivity)
            binding.recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : {${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }


}