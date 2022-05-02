package com.example.automoviles_18401179_edssonperez.ui

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.automoviles_18401179_edssonperez.Auto
import com.example.automoviles_18401179_edssonperez.databinding.ActualizarAutoBinding

class ActualizarFragment : AppCompatActivity(){
    private lateinit var binding: ActualizarAutoBinding

    var idauto = ""
    var modelo = ""
    var marca = ""
    var kilometraje = ""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        
        binding = ActualizarAutoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        idauto = intent.extras?.getString("idauto")?:""
        modelo = intent.extras?.getString("modelo")?:""
        marca = intent.extras?.getString("marca")?:""
        kilometraje = intent.extras?.getString("kilometraje")?:""

        binding.txtModelo.setText(modelo)
        binding.txtMarca.setText(marca)
        binding.txtKm.setText(kilometraje)

        val auto = Auto(this).mostrar(idauto.toInt())

        binding.btnAgreAuto.setOnClickListener {
            if(binding.txtModelo.text.toString() == "" ||binding.txtMarca.text.toString()==("")||binding.txtKm.text.toString()==("")){
                Toast.makeText(this,"Campos vacios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auto.modelo=binding.txtModelo.text.toString()
            auto.marca=binding.txtMarca.text.toString()
            auto.kilometraje=binding.txtKm.text.toString().toInt()

            val resultado = auto.actualizar()
            if(resultado){
                Toast.makeText(this,"Se actualizo con exito, vuelva a cargar la pagina",Toast.LENGTH_LONG).show()
                finish()
            }else{
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("No se pudo actualizar")
                    .show()
            }

        }


    }


}