package com.example.automoviles_18401179_edssonperez.ui.notifications

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.icu.text.Edits
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
import com.example.automoviles_18401179_edssonperez.databinding.FragmentModAutoBinding
import com.example.automoviles_18401179_edssonperez.ui.ActualizarFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.core.Query


class NotificationsFragment : Fragment() {
    val bd = FirebaseFirestore.getInstance()
    private var _binding: FragmentModAutoBinding? = null
    var idA= arrayListOf<String>()
    private val binding get() = _binding!!
    var arrendamientos= arrayListOf<String>()
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

            binding.txtModeloCon.setText("")
            binding.txtMarcaCon.setText("")
            binding.txtKmCon.setText("")

        }
        mostrarl()

        return root
    }
    fun agregaraListView(query: QuerySnapshot?){

        var arreglo = ArrayList<Auto>()
        arreglo.clear()
        for (documento in query!!) {
            contexto {
                var auto = Auto(requireContext())

                auto.idauto = documento.id
                auto.modelo = documento.getString("modelo").toString()
                auto.marca = documento.getString("marca").toString()
                auto.kilometraje = documento.getLong("kilometraje").toString().toInt()

                arreglo.add(auto)
            }
        }

        val coches = ArrayList<String>()
        //muestra en listview
        (0..arreglo.size - 1).forEach {
            val au = arreglo.get(it)

            coches.add(
                "Modelo:${au.modelo}, " +
                        "Marca:${au.marca}, Km:${au.kilometraje}"
            )
            idA.add(au.idauto)
        }
        contexto {
            binding.listaAutos.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1, coches
            )
        }
    }

    fun mostrarl() {
        idA.clear()
        //consulta
        bd.collection("auto")
            .addSnapshotListener { query, error ->
                if (error != null) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(error.message)
                        .show()
                    return@addSnapshotListener
                }
                agregaraListView(query!!)
            }
        binding.listaAutos.setOnItemClickListener { adapterView, view, i, l ->
            val c = idA.get(i)
            val coche = Auto(requireContext())
            buscar(c)
            if(!requireActivity().isFinishing) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Seleccionado")
                    //elimina
                    .setNegativeButton("Eliminar") { d, i ->
                        if (arrendamientos.size > 0) {
                            Toast.makeText(
                                requireContext(),
                                "No se puede eliminar, Se esta usando en arrendamientos",
                                Toast.LENGTH_LONG
                            ).show()
                        } else
                        coche.eliminar(c)
                        mostrarl()
                    }
                    //actualizar
                    .setPositiveButton("Actualizar") { d, i ->
                        val ventana = Intent(requireContext(), ActualizarFragment::class.java)
                        ventana.putExtra("idauto", c)
                        startActivity(ventana)
                    }
                    .setNeutralButton("Cerrar") { d, i -> }
                    .show()
            }
        }
    }
    fun buscar(ida:String) {
        arrendamientos.clear()

        bd.collection("arrendamiento")
            .whereEqualTo("idauto", ida).addSnapshotListener { query, error ->
                if (error != null) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(error.message)
                        .show()
                    return@addSnapshotListener
                }

                for (documento in query!!) {
                    contexto {
                        var arre = Arrendamiento(requireContext())
                        arre.idarre = documento.id
                        arrendamientos.add(arre.idarre)
                    }
                }
            }
    }
    fun mostrarList(mo:String, mar:String,km:Int){
        idA.clear()

        if(mo!="" && mar=="" && km==0) {
            bd.collection("auto")
                .whereEqualTo("modelo",mo).addSnapshotListener{ query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }


        }else
            if(mo=="" && mar!="" && km==0) {
                bd.collection("auto")
                    .whereEqualTo("marca",mar).addSnapshotListener{ query, error ->
                        if (error != null) {
                            AlertDialog.Builder(requireContext())
                                .setMessage(error.message)
                                .show()
                            return@addSnapshotListener
                        }
                        agregaraListView(query!!)
                    }
            }else

                if(mo=="" && mar=="" && km!=0) {
                    bd.collection("auto")
                        .whereEqualTo("kilometraje",km).addSnapshotListener{ query, error ->
                            if (error != null) {
                                AlertDialog.Builder(requireContext())
                                    .setMessage(error.message)
                                    .show()
                                return@addSnapshotListener
                            }
                            agregaraListView(query!!)
                        }
                }else

                    if(mo!="" && mar!="" && km==0) {
                        bd.collection("auto")
                            .whereEqualTo("modelo",mo).whereEqualTo("marca",mar).addSnapshotListener{ query, error ->
                                if (error != null) {
                                    AlertDialog.Builder(requireContext())
                                        .setMessage(error.message)
                                        .show()
                                    return@addSnapshotListener
                                }
                                agregaraListView(query!!)
                            }
                    }else

                        if(mo!="" && mar=="" && km!=0) {
                            bd.collection("auto")
                                .whereEqualTo("modelo",mo).whereEqualTo("kilometraje",km).addSnapshotListener{ query, error ->
                                    if (error != null) {
                                        AlertDialog.Builder(requireContext())
                                            .setMessage(error.message)
                                            .show()
                                        return@addSnapshotListener
                                    }
                                    agregaraListView(query!!)
                                }
                        }else

                            if(mo=="" && mar!="" && km!=0) {
                                bd.collection("auto")
                                    .whereEqualTo("marca",mar).whereEqualTo("kilometraje",km).addSnapshotListener{ query, error ->
                                        if (error != null) {
                                            AlertDialog.Builder(requireContext())
                                                .setMessage(error.message)
                                                .show()
                                            return@addSnapshotListener
                                        }
                                        agregaraListView(query!!)
                                    }
                            }else
                                if(mo!="" && mar!="" && km!=0) {
                                    bd.collection("auto")
                                        .whereEqualTo("modelo",mo).whereEqualTo("marca",mar)
                                        .whereEqualTo("kilometraje",km).addSnapshotListener{ query, error ->
                                            if (error != null) {
                                                AlertDialog.Builder(requireContext())
                                                    .setMessage(error.message)
                                                    .show()
                                                return@addSnapshotListener
                                            }
                                            agregaraListView(query!!)
                                        }
                                }else{
                                    Toast.makeText(requireContext(),"No encontraron resultados, mostrando todos", Toast.LENGTH_LONG).show()
                                    mostrarl()
                                }


    }

    fun contexto(op:Context.() ->Unit){
        if(isAdded&&context!=null){
            op(requireContext())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}