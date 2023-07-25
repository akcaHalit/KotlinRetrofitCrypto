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
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener{
    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager



        loadData()


    }

    private fun loadData() {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        println("SERVİCE ÖNCESİ")
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()


        /*
        service.getData().enqueue(object : Callback<CryptoModel> {

            override fun onResponse(call: Call<CryptoModel>, response: Response<CryptoModel>) {
                if (response.isSuccessful) {
                    val data = response.body() // API'den dönen verileri burada alırsınız.
                    // Yapılacak işlemler
                } else {
                    // Hatalı durumda yapılacak işlemler
                }
            }

            override fun onFailure(call: Call<CryptoModel>, t: Throwable) {
                // Hata durumunda yapılacak işlemler
            }
        })


         */
        println("SERVİCE SONRASI")



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



                    /*
                    val data = response.body()
                    println("SUCCESSFULL")
                    if(data != null){
                        for(cryptoModel : CryptoModel in data){
                            val price = cryptoModel.price
                            val currency = cryptoModel.currency

                            val crypto = CryptoModel(price,currency)
                            cryptoModels!!.add(crypto)
                        }
                        println(cryptoModels)

                        print(data)
                        println("DATA")
                    }else{
                        println("NULL")
                    }
                     */

            }
        })




    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : {${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }


}