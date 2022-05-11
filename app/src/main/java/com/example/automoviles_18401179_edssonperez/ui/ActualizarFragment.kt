package com.example.automoviles_18401179_edssonperez.ui

import android.R
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.automoviles_18401179_edssonperez.Auto
import com.example.automoviles_18401179_edssonperez.MainActivity
import com.example.automoviles_18401179_edssonperez.databinding.ActualizarAutoBinding
import com.google.firebase.firestore.FirebaseFirestore

class ActualizarFragment: AppCompatActivity() {

    private lateinit var binding: ActualizarAutoBinding
    val bd = FirebaseFirestore.getInstance()
    var idauto = ""
    var modelo = ""
    var marca = ""
    var kilometraje = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActualizarAutoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        idauto = intent.extras?.getString("idauto") ?: ""

        bd.collection("auto")
            .document(idauto)
            .get()
            .addOnSuccessListener {
                binding.txtModelo.setText(it.getString("modelo"))
                binding.txtMarca.setText(it.getString("marca"))
                binding.txtKm.setText(it.getLong("kilometraje").toString())
            }

        val auto = Auto(this)

        binding.btnAgreAuto.setOnClickListener {
            if (binding.txtModelo.text.toString() == "" || binding.txtMarca.text.toString() == ("") || binding.txtKm.text.toString() == ("")) {
                Toast.makeText(this, "Campos vacios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auto.modelo = binding.txtModelo.text.toString()
            auto.marca = binding.txtMarca.text.toString()
            auto.kilometraje = binding.txtKm.text.toString().toInt()

            auto.actualizar(idauto)
            this.finish()
        }


    }


}