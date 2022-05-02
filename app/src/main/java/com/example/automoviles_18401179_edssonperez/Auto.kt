package com.example.automoviles_18401179_edssonperez

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.widget.Toast
import androidx.fragment.app.Fragment

class Auto (este:Context){
    private var este = este
    var idauto = 0
    var modelo =""
    var marca = ""
    var kilometraje = 0
    private var err = ""

    fun insertar():Boolean {
        var bd = BaseDatos(este,"auto", null,1)
        err = ""
        try {
            val tabla = bd.writableDatabase
            var datos = ContentValues()

            datos.put("MODELO",modelo)
            datos.put("MARCA",marca)
            datos.put("KILOMETRAJE",kilometraje)

            var resultado = tabla.insert("AUTO",null,datos)
            if(resultado == -1L){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            bd.close()
        }
        return true
    }

    fun mostrarTodos() :ArrayList<Auto>{
        var bd = BaseDatos(este,"auto", null,1)
        var arreglo = ArrayList<Auto>()
        err = ""

        try {
            var tabla = bd.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTO"

            var cursor = tabla.rawQuery(SQL_SELECT,null)
            if(cursor.moveToFirst()){
                do {
                    val auto = Auto(este)
                    auto.idauto = cursor.getInt(0)
                    auto.modelo = cursor.getString(1)
                    auto.marca = cursor.getString(2)
                    auto.kilometraje = cursor.getInt(3)

                    arreglo.add(auto)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            bd.close()
        }
        return arreglo
    }

    fun mostrar(ida:Int) :Auto{
        var bd = BaseDatos(este,"auto", null,1)
        var auto = Auto(este)
        err = ""

        try {
            var tabla = bd.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTO WHERE IDAUTO=?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(ida.toString()))
            if(cursor.moveToFirst()){
                    auto.idauto = cursor.getInt(0)
                    auto.modelo = cursor.getString(1)
                    auto.marca = cursor.getString(2)
                    auto.kilometraje = cursor.getInt(3)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            bd.close()
        }
        return auto
    }
    fun actualizar(): Boolean{
        var bd = BaseDatos(este,"auto", null,1)
        try {
            var tabla = bd.writableDatabase
            val datos = ContentValues()
            datos.put("MODELO",modelo)
            datos.put("MARCA",marca)
            datos.put("KILOMETRAJE",kilometraje)


            val respuesta = tabla.update("AUTO",datos,"IDAUTO=?"
                ,arrayOf(idauto.toString()))
            if(respuesta==0){
                return false
            }

        }catch (err: SQLiteException){
            AlertDialog.Builder(este)
                .setTitle("Error")
                .setMessage(err.message!!)
                .show()
            return false
        }finally {
            bd.close()
        }
        return true
    }

    fun eliminar():Boolean{
        var bd = BaseDatos(este,"auto", null,1)
        var auto = Auto(este)
        err = ""

        try {
            var tabla = bd.writableDatabase
            var resultado = tabla.delete("AUTO","IDAUTO=?", arrayOf(idauto.toString()))

            if(resultado==0){
                return false
            }
        }catch (err: SQLiteException){
            AlertDialog.Builder(este)
                .setTitle("Error")
                .setMessage(err.message!!)
                .show()
            return false
        }finally {
            bd.close()
        }
        return true
    }
    fun mostrar(mo:String,mar:String,km:Int) :ArrayList<Auto>{
        var bd = BaseDatos(este,"auto", null,1)
        var arreglo = ArrayList<Auto>()
        err = ""
        try {
            var tabla = bd.readableDatabase
            if(mo!="" && mar=="" && km==0) {
                var SQL_SELECT = "SELECT * FROM AUTO WHERE MODELO=?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(mo))
                if (cursor.moveToFirst()) {
                    do {
                        val auto = Auto(este)
                        auto.idauto = cursor.getInt(0)
                        auto.modelo = cursor.getString(1)
                        auto.marca = cursor.getString(2)
                        auto.kilometraje = cursor.getInt(3)

                        arreglo.add(auto)
                    } while (cursor.moveToNext())
                }
            }else
            if(mo=="" && mar!="" && km==0) {
                var SQL_SELECT = "SELECT * FROM AUTO WHERE MARCA=?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(mar))
                if (cursor.moveToFirst()) {
                    do {
                        val auto = Auto(este)
                        auto.idauto = cursor.getInt(0)
                        auto.modelo = cursor.getString(1)
                        auto.marca = cursor.getString(2)
                        auto.kilometraje = cursor.getInt(3)

                        arreglo.add(auto)
                    } while (cursor.moveToNext())
                }
            }else

            if(mo=="" && mar=="" && km!=0) {
                var SQL_SELECT = "SELECT * FROM AUTO WHERE KILOMETRAJE=?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(km.toString()))
                if (cursor.moveToFirst()) {
                    do {
                        val auto = Auto(este)
                        auto.idauto = cursor.getInt(0)
                        auto.modelo = cursor.getString(1)
                        auto.marca = cursor.getString(2)
                        auto.kilometraje = cursor.getInt(3)

                        arreglo.add(auto)
                    } while (cursor.moveToNext())
                }
            }else

            if(mo!="" && mar!="" && km==0) {
                var SQL_SELECT = "SELECT * FROM AUTO WHERE MARCA=? AND MODELO=?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(mar,mo))
                if (cursor.moveToFirst()) {
                    do {
                        val auto = Auto(este)
                        auto.idauto = cursor.getInt(0)
                        auto.modelo = cursor.getString(1)
                        auto.marca = cursor.getString(2)
                        auto.kilometraje = cursor.getInt(3)

                        arreglo.add(auto)
                    } while (cursor.moveToNext())
                }
            }else

            if(mo!="" && mar=="" && km!=0) {
                var SQL_SELECT = "SELECT * FROM AUTO WHERE MODELO=? AND KILOMETRAJE=?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(mo,km.toString()))
                if (cursor.moveToFirst()) {
                    do {
                        val auto = Auto(este)
                        auto.idauto = cursor.getInt(0)
                        auto.modelo = cursor.getString(1)
                        auto.marca = cursor.getString(2)
                        auto.kilometraje = cursor.getInt(3)

                        arreglo.add(auto)
                    } while (cursor.moveToNext())
                }
            }else

            if(mo=="" && mar!="" && km!=0) {
                var SQL_SELECT = "SELECT * FROM AUTO WHERE MARCA=? AND KILOMETRAJE=?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(mar,km.toString()))
                if (cursor.moveToFirst()) {
                    do {
                        val auto = Auto(este)
                        auto.idauto = cursor.getInt(0)
                        auto.modelo = cursor.getString(1)
                        auto.marca = cursor.getString(2)
                        auto.kilometraje = cursor.getInt(3)

                        arreglo.add(auto)
                    } while (cursor.moveToNext())
                }
            }else
            if(mo!="" && mar!="" && km!=0) {
                var SQL_SELECT = "SELECT * FROM AUTO WHERE MODELO=? AND MARCA=? AND KILOMETRAJE =?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(mo,mar,km.toString()))
                if (cursor.moveToFirst()) {
                    do {
                        val auto = Auto(este)
                        auto.idauto = cursor.getInt(0)
                        auto.modelo = cursor.getString(1)
                        auto.marca = cursor.getString(2)
                        auto.kilometraje = cursor.getInt(3)

                        arreglo.add(auto)
                    } while (cursor.moveToNext())
                }
            }else{
                Toast.makeText(este,"No encontraron resultados", Toast.LENGTH_LONG).show()
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            bd.close()
        }
        return arreglo
    }

    /*
    fun insertar():Boolean {
        var bd = BaseDatos(este,"auto", null,1)
        try {

        }catch (err: SQLiteException){

        }finally {
            bd.close()
        }
    }*/
}