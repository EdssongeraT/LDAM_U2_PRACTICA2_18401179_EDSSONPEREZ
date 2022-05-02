package com.example.automoviles_18401179_edssonperez

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Arrendamiento (este: Context){
    private var este = este
    var idarre = 0
    var nombre=""
    var domicilio=""
    var licencia = ""
    var idAuto = 0
    var fecha = ""
    var modelo = ""
    var marca = ""
    private var err = ""

    fun insertar():Boolean {
        var bd = BaseDatos(este,"arrendamiento", null,1)
        err = ""
        try {
            val tabla = bd.writableDatabase
            var datos = ContentValues()

            datos.put("NOMBRE",nombre)
            datos.put("DOMICILIO",domicilio)
            datos.put("LICENCIA",licencia)
            datos.put("IDAUTO",idAuto)
            datos.put("FECHA",fecha)

            var resultado = tabla.insert("ARRENDAMIENTO",null,datos)
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

    fun mostrarTodos() :ArrayList<Arrendamiento>{
        var bd = BaseDatos(este,"arrendamiento", null,1)
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            var tabla = bd.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO "
        //Realmente trate de hacerlo con modelos y marcas, pero no me funcionaba ni los joins, unions, wheres
            var cursor = tabla.rawQuery(SQL_SELECT,null)

            if(cursor.moveToFirst()){
                do {
                    val arre = Arrendamiento(este)
                    arre.idarre = cursor.getInt(0)
                    arre.nombre = cursor.getString(1)
                    arre.domicilio = cursor.getString(2)
                    arre.licencia = cursor.getString(3)
                    arre.idAuto= cursor.getInt(4)
                    arre.fecha = cursor.getString(5)
                    //arre.modelo= cursor.getString(6)
                    //arre.marca = cursor.getString(7)

                    arreglo.add(arre)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            bd.close()
        }
        return arreglo
    }

    fun llave(num:Int):Boolean{
        var bd = BaseDatos(este,"auto", null,1)

        try {
            var tabla = bd.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTO WHERE IDAUTO=?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(num.toString()))

            if(cursor.moveToFirst()){
                    return true
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            bd.close()
        }
        return false
    }
    fun mostrar(ida:Int) :Arrendamiento{
        var bd = BaseDatos(este,"arrendamiento", null,1)
        var arre = Arrendamiento(este)
        err = ""

        try {
            var tabla = bd.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDA=?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(ida.toString()))
            if(cursor.moveToFirst()){
                arre.idarre = cursor.getInt(0)
                arre.nombre = cursor.getString(1)
                arre.domicilio = cursor.getString(2)
                arre.licencia = cursor.getString(3)
                arre.idAuto= cursor.getInt(4)
                arre.fecha = cursor.getString(5)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            bd.close()
        }
        return arre
    }
    fun actualizar(): Boolean{
        var bd = BaseDatos(este,"arrendamiento", null,1)
        try {
            var tabla = bd.writableDatabase
            val datos = ContentValues()
            datos.put("NOMBRE",nombre)
            datos.put("DOMICILIO",domicilio)
            datos.put("LICENCIA",licencia)
            datos.put("IDAUTO",idAuto)
            datos.put("FECHA",fecha)


            val respuesta = tabla.update("ARRENDAMIENTO",datos,"IDA=?"
                ,arrayOf(idarre.toString()))
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
        var bd = BaseDatos(este,"arrendamiento", null,1)
        var arre = Arrendamiento(este)
        err = ""

        try {
            var tabla = bd.writableDatabase
            var resultado = tabla.delete("ARRENDAMIENTO","IDA=?", arrayOf(idarre.toString()))

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

    fun mostrar(nom:String,dom:String,lic:String) :ArrayList<Arrendamiento>{
        var bd = BaseDatos(este,"arrendamiento", null,1)
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            if( nom!="" && dom=="" && lic=="" ) {
                var tabla = bd.readableDatabase
                var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE NOMBRE =? "

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(nom))
                if (cursor.moveToFirst()) {
                    do {
                        val arre = Arrendamiento(este)
                        arre.idarre = cursor.getInt(0)
                        arre.nombre = cursor.getString(1)
                        arre.domicilio = cursor.getString(2)
                        arre.licencia = cursor.getString(3)
                        arre.idAuto = cursor.getInt(4)
                        arre.fecha = cursor.getString(5)

                        arreglo.add(arre)
                    } while (cursor.moveToNext())
                }
            }else
            if( nom=="" && dom!="" && lic=="" ) {
                var tabla = bd.readableDatabase
                var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE DOMICILIO =? "

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(dom))
                if (cursor.moveToFirst()) {
                    do {
                        val arre = Arrendamiento(este)
                        arre.idarre = cursor.getInt(0)
                        arre.nombre = cursor.getString(1)
                        arre.domicilio = cursor.getString(2)
                        arre.licencia = cursor.getString(3)
                        arre.idAuto = cursor.getInt(4)
                        arre.fecha = cursor.getString(5)

                        arreglo.add(arre)
                    } while (cursor.moveToNext())
                }
            }else
            if( nom=="" && dom=="" && lic!="" ) {
                var tabla = bd.readableDatabase
                var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE LICENCIA =? "

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(lic))
                if (cursor.moveToFirst()) {
                    do {
                        val arre = Arrendamiento(este)
                        arre.idarre = cursor.getInt(0)
                        arre.nombre = cursor.getString(1)
                        arre.domicilio = cursor.getString(2)
                        arre.licencia = cursor.getString(3)
                        arre.idAuto = cursor.getInt(4)
                        arre.fecha = cursor.getString(5)

                        arreglo.add(arre)
                    } while (cursor.moveToNext())
                }
            }else
            if( nom!="" && dom!="" && lic=="" ) {
                var tabla = bd.readableDatabase
                var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE NOMBRE =? AND DOMICILIO =? "

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(nom,dom))
                if (cursor.moveToFirst()) {
                    do {
                        val arre = Arrendamiento(este)
                        arre.idarre = cursor.getInt(0)
                        arre.nombre = cursor.getString(1)
                        arre.domicilio = cursor.getString(2)
                        arre.licencia = cursor.getString(3)
                        arre.idAuto = cursor.getInt(4)
                        arre.fecha = cursor.getString(5)

                        arreglo.add(arre)
                    } while (cursor.moveToNext())
                }
            }else
            if( nom!="" && dom=="" && lic!="" ) {
                var tabla = bd.readableDatabase
                var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE NOMBRE =? AND LICENCIA =?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(nom, lic))
                if (cursor.moveToFirst()) {
                    do {
                        val arre = Arrendamiento(este)
                        arre.idarre = cursor.getInt(0)
                        arre.nombre = cursor.getString(1)
                        arre.domicilio = cursor.getString(2)
                        arre.licencia = cursor.getString(3)
                        arre.idAuto = cursor.getInt(4)
                        arre.fecha = cursor.getString(5)

                        arreglo.add(arre)
                    } while (cursor.moveToNext())
                }
            }else
            if( nom=="" && dom!="" && lic!="" ) {
                var tabla = bd.readableDatabase
                var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE DOMICILIO =? AND LICENCIA =? "

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(dom, lic))
                if (cursor.moveToFirst()) {
                    do {
                        val arre = Arrendamiento(este)
                        arre.idarre = cursor.getInt(0)
                        arre.nombre = cursor.getString(1)
                        arre.domicilio = cursor.getString(2)
                        arre.licencia = cursor.getString(3)
                        arre.idAuto = cursor.getInt(4)
                        arre.fecha = cursor.getString(5)

                        arreglo.add(arre)
                    } while (cursor.moveToNext())
                }
            }else
            if( nom!="" && dom!="" && lic!="" ) {
                var tabla = bd.readableDatabase
                var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE NOMBRE =? AND DOMICILIO =? AND LICENCIA =?"

                var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(nom,dom,lic))
                if (cursor.moveToFirst()) {
                    do {
                        val arre = Arrendamiento(este)
                        arre.idarre = cursor.getInt(0)
                        arre.nombre = cursor.getString(1)
                        arre.domicilio = cursor.getString(2)
                        arre.licencia = cursor.getString(3)
                        arre.idAuto = cursor.getInt(4)
                        arre.fecha = cursor.getString(5)

                        arreglo.add(arre)
                    } while (cursor.moveToNext())
                }
            }else{
            AlertDialog.Builder(este)
                .setTitle("Error")
                .setMessage("No se encontraron coincidencias")
                .show()
        }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            bd.close()
        }
        return arreglo
    }
}