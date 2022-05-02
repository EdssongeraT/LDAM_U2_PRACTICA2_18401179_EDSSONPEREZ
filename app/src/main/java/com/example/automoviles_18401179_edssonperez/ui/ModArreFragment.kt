package com.example.automoviles_18401179_edssonperez.ui

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.automoviles_18401179_edssonperez.Arrendamiento
import com.example.automoviles_18401179_edssonperez.Auto
import com.example.automoviles_18401179_edssonperez.databinding.FragmentModArreArreBinding


class ModArreFragment: Fragment() {
    private var _binding: FragmentModArreArreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var idA= arrayListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ModArreViewModel::class.java)

        _binding = FragmentModArreArreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnConArre.setOnClickListener {
            val arren = Arrendamiento(requireContext())
            arren.nombre = binding.txtNombreCon.text.toString()
            arren.domicilio = binding.txtDomicilioCon.text.toString()
            arren.licencia = binding.txtLicCon.text.toString()

            mostrarList(
                    arren.nombre,
                    arren.domicilio,
                    arren.licencia,

                )

        }
        binding.txtNombreCon.setText("")
        binding.txtDomicilioCon.setText("")
        binding.txtLicCon.setText("")
        return root
    }

    fun mostrarList(nom:String,dom:String,lic:String){
        var listaArre = Arrendamiento(requireContext()).mostrar(nom,dom,lic)
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
                    mostrarList(nom,dom,lic)
                }
                .setPositiveButton("Actualizar"){d,i->
                    val ventana = Intent(requireContext(), ActualizarArrendamiento::class.java)
                    ventana.putExtra("idarre",arren.idarre.toString())
                    ventana.putExtra("nombre",arren.nombre)
                    ventana.putExtra("domicilio",arren.domicilio)
                    ventana.putExtra("licencia",arren.licencia)
                    ventana.putExtra("idauto",arren.idAuto.toString())
                    ventana.putExtra("fecha",arren.fecha.toString())
                    startActivity(ventana)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}