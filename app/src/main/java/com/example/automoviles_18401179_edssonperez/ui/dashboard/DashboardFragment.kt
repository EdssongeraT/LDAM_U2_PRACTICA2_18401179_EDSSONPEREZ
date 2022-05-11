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
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.automoviles_18401179_edssonperez.Arrendamiento
import com.example.automoviles_18401179_edssonperez.Auto
import com.example.automoviles_18401179_edssonperez.databinding.FragmentAgreArreBinding
import com.example.automoviles_18401179_edssonperez.ui.ActualizarArrendamiento
import com.example.automoviles_18401179_edssonperez.ui.ActualizarFragment
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {
    val bd = FirebaseFirestore.getInstance()
    private var _binding: FragmentAgreArreBinding? = null
    private val binding get() = _binding!!
    var cursor: Cursor? = null
    var id_auto =""
    var mod_auto =""
    var mar_auto = ""
    var km_auto = 0
    var idA= arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentAgreArreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.txtModeloArre.isEnabled =false
        binding.btnSel.setOnClickListener {
            idA.clear()

            bd.collection("auto")
                .addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }

                    var arreglo = ArrayList<Auto>()
                    arreglo.clear()
                    for (documento in query!!) {
                        var auto = Auto(requireContext())
                        auto.idauto = documento.id
                        auto.modelo = documento.getString("modelo").toString()
                        auto.marca = documento.getString("marca").toString()
                        auto.kilometraje = documento.getLong("kilometraje").toString().toInt()

                        arreglo.add(auto)
                    }

                    val coches = ArrayList<String>()
                    //muestra en listview
                    (0..arreglo.size - 1).forEach {
                        val au = arreglo.get(it)
                        coches.add(
                            "Modelo:${au.modelo}, " +
                                    "Marca:${au.marca}, Km:${au.kilometraje}"
                        )
                        print(au.modelo)
                        idA.add(au.idauto)
                    }
                    AlertDialog.Builder(requireContext()).
                    setItems(coches.toTypedArray()){dialog, i ->
                        id_auto=arreglo.get(i).idauto
                        mod_auto=arreglo.get(i).modelo
                        mar_auto=arreglo.get(i).marca
                        km_auto=arreglo.get(i).kilometraje.toString().toInt()

                        binding.txtModeloArre.setText(id_auto)
                    }
                            .setNeutralButton("Cerrar") { d, i -> }
                        .show()
                }


        }
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
            arre.idAuto = binding.txtModeloArre.text.toString()
            arre.fecha = binding.txtFecha.text.toString()
            arre.modelo = mod_auto
            arre.marca = mar_auto
            arre.kilometraje = km_auto.toInt()
        val auto = Auto(requireContext())

            binding.txtNombre.setText("")
            binding.txtDomicilio.setText("")
            binding.txtLic.setText("")
            binding.txtModeloArre.setText("")
            binding.txtFecha.setText("")

            arre.insertar()
    }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onResume() {
        super.onResume()

    }
}