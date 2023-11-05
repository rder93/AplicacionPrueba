package com.example.aplicacionprueba

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacionprueba.databinding.ActivityListaBinding
import org.json.JSONObject

class ListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaBinding

    private lateinit var connectivityChecker: NetworkConnectivityChecker
    private var lista: ArrayList<ObjetoLista> = ArrayList<ObjetoLista>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectivityChecker = NetworkConnectivityChecker(this)

        setSupportActionBar(binding.toolbar)
        // Habilitar la flecha de retroceso
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        buscarDatosLista()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    fun buscarDatosLista() {

        if (connectivityChecker.isConnectedToInternet()) {
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "https://dummyjson.com/products?limit=10"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->

                    print(response)
                    try {
                        val jsonResponse = JSONObject(response)
                        val productos = jsonResponse.getJSONArray("products")

                        for (i in 0..productos.length() - 1){
                            val texto = productos.getJSONObject(i).getString("title")
                            val imagenURL = productos.getJSONObject(i).getString("thumbnail")
                            val description = productos.getJSONObject(i).getString("description")

                            val producto = ObjetoLista(texto, description, imagenURL)
                            lista.add(producto)
                        }

                        drawList(lista)

                    }
                    catch (e: java.lang.Exception){
                        e.printStackTrace()
                    }

                },
                {
                    it.printStackTrace()
                })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
        else{
            Toast.makeText(this, "No hay conexión a internet!", Toast.LENGTH_SHORT).show()
        }

    }

    fun drawList(lista: ArrayList<ObjetoLista>){
        binding.recyclerview.setHasFixedSize(true)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerview.layoutManager = llm
        val adaptador = Adapter(applicationContext,lista)
        binding.recyclerview.adapter = adaptador
    }

}