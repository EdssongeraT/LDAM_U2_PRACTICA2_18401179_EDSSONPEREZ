package com.example.automoviles_18401179_edssonperez

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Auto (este:Context){
    private var este = este
    var idauto = ""
    var modelo =""
    var marca = ""
    var kilometraje = 0

    private var err = ""

    fun insertar(){
        val bd = FirebaseFirestore.getInstance()
        val datos = hashMapOf(
            "modelo" to modelo,
            "marca" to marca,
            "kilometraje" to kilometraje
        )
        bd.collection("auto").add(datos)
            .addOnSuccessListener {

                Toast.makeText(este, "Se inserto on exito",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                AlertDialog.Builder(este)
                    .setMessage(it.message)
                    .show()
            }

    }


    fun actualizar(ida: String){
            val bd = FirebaseFirestore.getInstance()
            bd.collection("auto")
                .document(ida)
                .update("modelo",modelo,"marca",marca,"kilometraje",kilometraje)
                .addOnSuccessListener {

                    Toast.makeText(este, "Se actualizo on exito",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    AlertDialog.Builder(este)
                        .setMessage(it.message)
                        .show()
                }

    }

    fun eliminar(ida:String){
        val bd = FirebaseFirestore.getInstance()

            bd.collection("auto")
                .document(ida)
                .delete()
                .addOnSuccessListener {

                    Toast.makeText(este, "Se elimino on exito", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    AlertDialog.Builder(este)
                        .setMessage(it.message)
                        .show()
                }
    }



}