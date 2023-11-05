package com.example.aplicacionprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLista = findViewById<Button>(R.id.buttonLista)

        buttonLista.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        })

        val buttonMapa = findViewById<Button>(R.id.buttonMapa)

        buttonMapa.setOnClickListener(View.OnClickListener {

        })

    }
}