package com.example.automoviles_18401179_edssonperez.ui.dashboard

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.automoviles_18401179_edssonperez.Arrendamiento
import com.example.automoviles_18401179_edssonperez.Auto
import com.example.automoviles_18401179_edssonperez.databinding.FragmentAgreArreBinding
import com.example.automoviles_18401179_edssonperez.ui.ActualizarArrendamiento
import com.example.automoviles_18401179_edssonperez.ui.ActualizarFragment

class DashboardFragment : Fragment() {

    private var _binding: FragmentAgreArreBinding? = null
    private val binding get() = _binding!!
    var cursor: Cursor? = null
    var id_auto =0
    var mod_auto =""
    var idA= arrayListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentAgreArreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarList()
        binding.btnAgreArre.setOnClickListener {
        if(binding.txtNombre.text.toString() == ""||binding.txtDomicilio.text.toString()== ""||binding.txtLic.text.toString() == ""
            ||binding.txtModeloArre.text.toString() == ""||binding.txtFecha.text.toString() == ""){
            Toast.makeText(requireContext(),"Campos vacios", Toast.LENGTH_LONG).show()
            return@setOnClickListener
        }
        val arre= Arrendamiento(requireContext())
            arre.nombre= binding.txtNombre.text.toString()
            arre.domicilio= binding.txtDomicilio.text.toString()
            arre.licencia = binding.txtLic.text.toString()
            arre.idAuto = binding.txtModeloArre.text.toString().toInt()
            arre.fecha = binding.txtFecha.text.toString()
            if (arre.llave(arre.idAuto)) {


        val resultado = arre.insertar()
        if(resultado){
            Toast.makeText(requireContext(),"Se inserto con exito", Toast.LENGTH_LONG).show()
            binding.txtNombre.setText("")
            binding.txtDomicilio.setText("")
            binding.txtLic.setText("")
            binding.txtModeloArre.setText("")
            binding.txtFecha.setText("")
        }else{
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("No se pudo insertar")
                .show()
        }

            }else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("No existe auto")
                    .show()
            }
            mostrarList()
    }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun mostrarList(){
        var listaArre = Arrendamiento(requireContext()).mostrarTodos()
        var arres = ArrayList<String>()
        (0..listaArre.size-1).forEach{
            val ar = listaArre.get(it)
            arres.add("Arrendamiento ${ar.idarre}, " +
                    "Nombre:${ar.nombre}, Domicilio:${ar.domicilio}, Licencia:${ar.licencia}"
                    +", idAuto:${ar.idAuto}, Fecha:${ar.fecha}")
            idA.add(ar.idarre)
        }
        binding.listaAutos.adapter = ArrayAdapter<String>(requireContext(),
            R.layout.simple_list_item_1,arres)
        binding.listaAutos.setOnItemClickListener { adapterView, view, i,l  ->
            val c = idA.get(i)
            val arren = Arrendamiento(requireContext()).mostrar(c)
            AlertDialog.Builder(requireContext())
                .setTitle("Seleccionado")
                .setMessage("Nombre: ${arren.nombre}\nDomicilio: ${arren.domicilio} \nLicencia: ${arren.licencia}\n" +
                        "coche: ${arren.idAuto}\n" +
                        "Fecha: ${arren.fecha}")
                .setNegativeButton("Eliminar"){d,i->
                    arren.eliminar()
                    mostrarList()
                }
                .setPositiveButton("Actualizar"){d,i->
                    val ventana = Intent(requireContext(), ActualizarArrendamiento::class.java)
                    ventana.putExtra("idarre",arren.idarre.toString())
                    ventana.putExtra("nombre",arren.nombre)
                    ventana.putExtra("domicilio",arren.domicilio)
                    ventana.putExtra("licencia",arren.licencia)
                    ventana.putExtra("idauto",arren.idAuto)
                    ventana.putExtra("fecha",arren.fecha)
                    startActivity(ventana)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }
    override fun onResume() {
        super.onResume()
        mostrarList()
    }
}