package com.example.automoviles_18401179_edssonperez.ui.home

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
import com.example.automoviles_18401179_edssonperez.databinding.FragmentAgreAutoBinding
import com.example.automoviles_18401179_edssonperez.ui.ActualizarFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentAgreAutoBinding? = null
    private val binding get() = _binding!!
    var idA= arrayListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentAgreAutoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnAgreAuto.setOnClickListener {

            if(binding.txtModelo.text.toString() == "" ||binding.txtMarca.text.toString()==("")||binding.txtKm.text.toString()==("")){
                Toast.makeText(requireContext(),"Campos vacios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val auto = Auto(requireContext())
            auto.modelo=binding.txtModelo.text.toString()
            auto.marca=binding.txtMarca.text.toString()
            auto.kilometraje=binding.txtKm.text.toString().toInt()

            val resultado = auto.insertar()
            if(resultado){
                Toast.makeText(requireContext(),"Se inserto con exito",Toast.LENGTH_LONG).show()
                binding.txtModelo.setText("")
                binding.txtMarca.setText("")
                binding.txtKm.setText("")
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("No se pudo insertar")
                    .show()
            }
            mostrarList()
        }
        mostrarList()
        return root
    }

    fun mostrarList(){
        var listaAutos = Auto(requireContext()).mostrarTodos()
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
                    mostrarList()
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

    override fun onResume() {
        super.onResume()
        mostrarList()
    }
}