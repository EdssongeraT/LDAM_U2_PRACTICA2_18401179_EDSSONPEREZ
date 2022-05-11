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
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    val bd = FirebaseFirestore.getInstance()
    private var _binding: FragmentAgreAutoBinding? = null
    private val binding get() = _binding!!
    var idA = arrayListOf<String>()

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
            if (binding.txtModelo.text.toString() == "" || binding.txtMarca.text.toString() == ("") || binding.txtKm.text.toString() == ("")) {
                Toast.makeText(requireContext(), "Campos vacios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val auto = Auto(requireContext())
            auto.modelo = binding.txtModelo.text.toString()
            auto.marca = binding.txtMarca.text.toString()
            auto.kilometraje = binding.txtKm.text.toString().toInt()

            auto.insertar()
            binding.txtModelo.setText("")
            binding.txtMarca.setText("")
            binding.txtKm.setText("")

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}