package com.example.automoviles_18401179_edssonperez.ui.notifications

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.automoviles_18401179_edssonperez.Auto
import com.example.automoviles_18401179_edssonperez.databinding.FragmentModAutoBinding
import com.example.automoviles_18401179_edssonperez.ui.ActualizarFragment


class NotificationsFragment : Fragment() {

    private var _binding: FragmentModAutoBinding? = null
    var idA= arrayListOf<Int>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentModAutoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnConAuto.setOnClickListener {
            val auto = Auto(requireContext())
            auto.modelo = binding.txtModeloCon.text.toString()
            auto.marca = binding.txtMarcaCon.text.toString()
            if(binding.txtKmCon.text.toString()=="")
                auto.kilometraje =0
            else
                auto.kilometraje=binding.txtKmCon.text.toString().toInt()
                mostrarList(auto.modelo, auto.marca, auto.kilometraje)

        }
        binding.txtModeloCon.setText("")
        binding.txtMarcaCon.setText("")
        binding.txtKmCon.setText("")
        return root
    }

    fun mostrarList(mod:String, marca:String,km:Int){
        var listaAutos = Auto(requireContext()).mostrar(mod,marca,km)
        var coches = ArrayList<String>()
        (0..listaAutos.size-1).forEach{
            val au = listaAutos.get(it)
            coches.add("Auto ${au.idauto}, Modelo:${au.modelo}, " +
                    "Marca:${au.marca}, Km:${au.kilometraje}")
            idA.add(au.idauto)
        }
        binding.listaAutos.adapter = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_list_item_1,coches)
        binding.listaAutos.setOnItemClickListener { adapterView, view, i,l  ->
            val c = idA.get(i)
            val coche = Auto(requireContext()).mostrar(c)
            AlertDialog.Builder(requireContext())
                .setTitle("Seleccionado")
                .setMessage("Modelo: ${coche.modelo}\nMarca: ${coche.marca} \nkilometraje: ${coche.kilometraje}")
                .setNegativeButton("Eliminar"){d,i->
                    coche.eliminar()
                    mostrarList(mod,marca,km)
                }
                .setPositiveButton("Actualizar"){d,i->
                    val ventana = Intent(requireContext(), ActualizarFragment::class.java)
                    ventana.putExtra("idauto",coche.idauto.toString())
                    ventana.putExtra("modelo",coche.modelo)
                    ventana.putExtra("marca",coche.marca)
                    ventana.putExtra("kilometraje",coche.kilometraje.toString())
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