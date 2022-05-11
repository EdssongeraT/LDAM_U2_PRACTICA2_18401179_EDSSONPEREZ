package com.example.automoviles_18401179_edssonperez

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Arrendamiento (este: Context){
    private var este = este
    var idarre = ""
    var nombre=""
    var domicilio=""
    var licencia = ""
    var idAuto = ""
    var fecha = ""
    var modelo = ""
    var marca = ""
    var kilometraje =0
    private var err = ""

    fun insertar() {
        val bd = FirebaseFirestore.getInstance()
        val datos = hashMapOf(
            "nombre" to nombre,
            "domicilio" to domicilio,
            "licencia" to licencia,
            "idauto" to idAuto,
            "fecha" to fecha,
            "modelo" to modelo,
            "marca" to marca,
            "kilometraje" to kilometraje
        )
        bd.collection("arrendamiento").add(datos)
            .addOnSuccessListener {

                Toast.makeText(este, "Se inserto on exito", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                AlertDialog.Builder(este)
                    .setMessage(it.message)
                    .show()
            }


    }

    fun actualizar(ida:String){
        val bd = FirebaseFirestore.getInstance()
        bd.collection("arrendamiento")
            .document(ida)
            .update("nombre",nombre,"domicilio",domicilio,"licencia",licencia
                ,"fecha",fecha,"idauto",idAuto,
                "modelo",modelo,"marca",marca,"kilometraje",kilometraje)
            .addOnSuccessListener {

                Toast.makeText(este, "Se actualizo on exito",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                AlertDialog.Builder(este)
                    .setMessage(it.message)
                    .show()
            }
    }

    fun eliminar(ida: String){
        val bd = FirebaseFirestore.getInstance()
        bd.collection("arrendamiento")
            .document(ida)
            .delete()
            .addOnSuccessListener {

                Toast.makeText(este, "Se elimino on exito",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                AlertDialog.Builder(este)
                    .setMessage(it.message)
                    .show()
            }
    }




}