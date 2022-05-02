package com.example.automoviles_18401179_edssonperez.ui

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.automoviles_18401179_edssonperez.Arrendamiento
import com.example.automoviles_18401179_edssonperez.databinding.ActualizarArrendamientoBinding

class ActualizarArrendamiento: AppCompatActivity() {
    private lateinit var binding: ActualizarArrendamientoBinding

    var idarre = ""
    var nombre = ""
    var domicilio= ""
    var licencia = ""
    var idauto = ""
    var fecha = ""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActualizarArrendamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        idarre = intent.extras?.getString("idauto")?:""
        nombre = intent.extras?.getString("nombre")?:""
        domicilio = intent.extras?.getString("domicilio")?:""
        licencia = intent.extras?.getString("licencia")?:""
        idauto = intent.extras?.getString("idauto")?:""
        fecha = intent.extras?.getString("fecha")?:""

        binding.txtNombre.setText(nombre)
        binding.txtDomicilio.setText(domicilio)
        binding.txtLic.setText(licencia)
        binding.txtModeloArre.setText(idauto)
        binding.txtFecha.setText(fecha)

        val arre = Arrendamiento(this).mostrar(1)

        binding.btnAgreArre.setOnClickListener {
            if(binding.txtNombre.text.toString() == ""||binding.txtDomicilio.text.toString()== ""||binding.txtLic.text.toString() == ""
                ||binding.txtModeloArre.text.toString() == ""||binding.txtFecha.text.toString() == ""){
                Toast.makeText(this,"Campos vacios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            arre.nombre= binding.txtNombre.text.toString()
            arre.domicilio= binding.txtDomicilio.text.toString()
            arre.licencia = binding.txtLic.text.toString()
            arre.idAuto = binding.txtModeloArre.text.toString().toInt()
            arre.fecha = binding.txtFecha.text.toString()
            if (arre.llave(arre.idAuto)) {


                val resultado = arre.insertar()
                if(resultado){
                    Toast.makeText(this,"Se actulizo con exito, vuelva a cargar la pagina", Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("No se pudo actualizar")
                        .show()
                }

            }else {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("No existe auto")
                    .show()
            }
        }


    }
}