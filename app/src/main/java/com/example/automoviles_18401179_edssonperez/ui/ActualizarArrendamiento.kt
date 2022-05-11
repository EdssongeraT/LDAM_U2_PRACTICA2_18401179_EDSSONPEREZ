package com.example.automoviles_18401179_edssonperez.ui

import android.R
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.automoviles_18401179_edssonperez.Arrendamiento
import com.example.automoviles_18401179_edssonperez.Auto
import com.example.automoviles_18401179_edssonperez.databinding.ActualizarArrendamientoBinding
import com.google.firebase.firestore.FirebaseFirestore

class ActualizarArrendamiento: AppCompatActivity() {
    private lateinit var binding: ActualizarArrendamientoBinding
    val bd = FirebaseFirestore.getInstance()
    var idA= arrayListOf<String>()
    var idarre = ""
    var nombre = ""
    var domicilio= ""
    var licencia = ""
    var idauto = ""
    var modelo = ""
    var marca =""
    var fecha = ""
    var km = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActualizarArrendamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtModeloArre.isEnabled =false
        idarre = intent.extras?.getString("idarre")?:""
        binding.btnSel.setOnClickListener {

            bd.collection("auto")
                .addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(this)
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }

                    var arreglo = ArrayList<Auto>()
                    arreglo.clear()
                    for (documento in query!!) {
                        var auto = Auto(this)
                        auto.idauto = documento.id
                        auto.modelo = documento.getString("modelo").toString()
                        auto.marca = documento.getString("marca").toString()
                        auto.kilometraje = documento.getLong("kilometraje").toString().toInt()

                        arreglo.add(auto)
                    }

                    val coches = ArrayList<String>()
                    //muestra en listview
                    (0..arreglo.size - 1).forEach {
                        val au = arreglo.get(it)
                        coches.add(
                            "Modelo:${au.modelo}, " +
                                    "Marca:${au.marca}, Km:${au.kilometraje}"
                        )
                        print(au.modelo)
                        idA.add(au.idauto)
                    }
                    if(!this.isFinishing) {
                        AlertDialog.Builder(this).setItems(coches.toTypedArray()) { dialog, i ->
                            idauto = arreglo.get(i).idauto
                            modelo = arreglo.get(i).modelo
                            marca = arreglo.get(i).marca
                            km = arreglo.get(i).kilometraje.toString().toInt()

                            binding.txtModeloArre.setText(idauto)
                        }
                            .setNeutralButton("Cerrar") { d, i -> }
                            .show()
                    }
                }

        }

        bd.collection("arrendamiento")
            .document(idarre)
            .get()
            .addOnSuccessListener {

                binding.txtNombre.setText(it.getString("nombre"))
                binding.txtDomicilio.setText(it.getString("domicilio"))
                binding.txtLic.setText(it.getString("licencia"))
                binding.txtModeloArre.setText(it.getString("idauto"))
                binding.txtFecha.setText(it.getString("fecha"))

            }
        val arre = Arrendamiento(this)

        binding.btnAgreArre.setOnClickListener {
            if(binding.txtNombre.text.toString() == ""||binding.txtDomicilio.text.toString()== ""||binding.txtLic.text.toString() == ""
                ||binding.txtModeloArre.text.toString() == ""||binding.txtFecha.text.toString() == ""){
                Toast.makeText(this,"Campos vacios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            arre.nombre= binding.txtNombre.text.toString()
            arre.domicilio= binding.txtDomicilio.text.toString()
            arre.licencia = binding.txtLic.text.toString()
            arre.idAuto = binding.txtModeloArre.text.toString()
            arre.modelo = modelo
            arre.marca = marca
            arre.kilometraje = km
            arre.fecha = binding.txtFecha.text.toString()

            arre.actualizar(idarre)
            this.finish()

        }


    }

}