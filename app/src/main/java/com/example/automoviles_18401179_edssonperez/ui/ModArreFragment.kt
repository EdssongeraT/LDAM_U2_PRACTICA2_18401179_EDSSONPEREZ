package com.example.automoviles_18401179_edssonperez.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.automoviles_18401179_edssonperez.Arrendamiento
import com.example.automoviles_18401179_edssonperez.databinding.FragmentModArreArreBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class ModArreFragment: Fragment() {
    private var _binding: FragmentModArreArreBinding? = null
    val bd = FirebaseFirestore.getInstance()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var idA= arrayListOf<String>()


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
            arren.modelo = binding.txtModeloCon.text.toString()
            arren.marca = binding.txtMarcaCon.text.toString()

            mostrar(
                    arren.nombre,
                    arren.domicilio,
                    arren.licencia,
                    arren.modelo,
                arren.marca
                )
            binding.txtNombreCon.setText("")
            binding.txtDomicilioCon.setText("")
            binding.txtLicCon.setText("")
            binding.txtModeloCon.setText("")
            binding.txtModeloCon.setText("")
        }
        mostrarl()

        return root
    }

    fun agregaraListView(query: QuerySnapshot?) {
        var arreglo = ArrayList<Arrendamiento>()
        arreglo.clear()
        for (documento in query!!) {
            contexto {
                var arre = Arrendamiento(requireContext())

                arre.idarre = documento.id
                arre.nombre = documento.getString("nombre").toString()
                arre.domicilio = documento.getString("domicilio").toString()
                arre.licencia = documento.getString("licencia").toString()
                arre.modelo = documento.getString("modelo").toString()
                arre.marca = documento.getString("marca").toString()

                arreglo.add(arre)
            }
        }

        val arres = ArrayList<String>()
        //muestra en listview
        (0..arreglo.size - 1).forEach {
            val ar= arreglo.get(it)

            arres.add(
                "Nombre:${ar.nombre}, " + "Domicilio:${ar.domicilio}, " + "Licencia:${ar.licencia}, "
                        +"Modelo:${ar.modelo}, " +  "Marca:${ar.marca}"
            )
            idA.add(ar.idarre)
        }
        contexto {
            binding.listaAutos.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1, arres
            )
        }
    }

    fun mostrarl() {
        idA.clear()

        //consulta
        bd.collection("arrendamiento")
            .addSnapshotListener { query, error ->
                if (error != null) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(error.message)
                        .show()
                    return@addSnapshotListener
                }

                agregaraListView(query)

            }
        binding.listaAutos.setOnItemClickListener { adapterView, view, i, l ->
            val c = idA.get(i)
            val arres = Arrendamiento(requireContext())

            if(!requireActivity().isFinishing) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Seleccionado")
                    //elimina
                    .setNegativeButton("Eliminar") { d, i ->

                            arres.eliminar(c)
                        mostrarl()

                    }
                    //actualizar
                    .setPositiveButton("Actualizar") { d, i ->
                        val ventana = Intent(requireContext(), ActualizarArrendamiento::class.java)
                        ventana.putExtra("idarre", c)
                        startActivity(ventana)
                    }
                    .setNeutralButton("Cerrar") { d, i -> }
                    .show()
            }
        }
    }

    fun mostrar(nom:String,dom:String,lic:String,mod:String,mar:String) {
        idA.clear()

        if (nom != "" && dom == "" && lic == "" && mod == "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom == "" && dom != "" && lic == "" && mod == "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("domicilio",dom).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom == "" && dom == "" && lic != "" && mod == "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("licencia",lic).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom == "" && dom == "" && lic == "" && mod != "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("modelo",mod).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom == "" && dom == "" && lic == "" && mod == "" && mar != "") {
            bd.collection("arrendamiento")
                .whereEqualTo("marca",mar).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom != "" && lic == "" && mod == "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("domicilio",dom).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom == "" && lic != "" && mod == "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("licencia",lic).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom == "" && lic == "" && mod != "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("modelo",mod).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom == "" && lic == "" && mod == "" && mar != "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("marca",mar).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom != "" && lic != "" && mod == "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("domicilio",dom)
                .whereEqualTo("licencia",lic).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom != "" && lic == "" && mod != "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("domicilio",dom)
                .whereEqualTo("modelo",mod).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom != "" && lic == "" && mod == "" && mar != "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("domicilio",dom)
                .whereEqualTo("marca",mar).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom == "" && lic != "" && mod != "" && mar == "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("modelo",mod)
                .whereEqualTo("licencia",lic).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
        } else
        if (nom != "" && dom == "" && lic != "" && mod == "" && mar != "") {
            bd.collection("arrendamiento")
                .whereEqualTo("nombre",nom).whereEqualTo("marca",mar)
                .whereEqualTo("licencia",lic).addSnapshotListener { query, error ->
                    if (error != null) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    agregaraListView(query!!)
                }
         } else
         if (nom != "" && dom == "" && lic == "" && mod != "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("nombre",nom).whereEqualTo("marca",mar)
                 .whereEqualTo("modelo",mod).addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom != "" && dom != "" && lic != "" && mod != "" && mar == "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("nombre",nom).whereEqualTo("domicilio",dom)
                 .whereEqualTo("licencia",lic).whereEqualTo("modelo",mod)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom != "" && dom != "" && lic != "" && mod == "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("nombre",nom).whereEqualTo("domicilio",dom)
                 .whereEqualTo("licencia",lic).whereEqualTo("marca",mar)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom != "" && dom == "" && lic != "" && mod != "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("nombre",nom).whereEqualTo("mar",mar)
                 .whereEqualTo("licencia",lic).whereEqualTo("modelo",mod)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom != "" && dom != "" && lic != "" && mod != "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("nombre",nom).whereEqualTo("domicilio",dom)
                 .whereEqualTo("licencia",lic).whereEqualTo("modelo",mod).whereEqualTo("marca",mar)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom != "" && lic != "" && mod == "" && mar == "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("domicilio",dom).whereEqualTo("licencia",lic)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom != "" && lic == "" && mod != "" && mar == "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("domicilio",dom).whereEqualTo("modelo",mod)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom != "" && lic == "" && mod == "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("domicilio",dom).whereEqualTo("marca",mar)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom != "" && lic != "" && mod != "" && mar == "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("domicilio",dom).whereEqualTo("licencia",lic)
                 .whereEqualTo("modelo",mod)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom != "" && lic != "" && mod == "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("domicilio",dom).whereEqualTo("licencia",lic)
                 .whereEqualTo("marca",mar)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom == "" && lic != "" && mod != "" && mar == "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("licencia",lic)
                 .whereEqualTo("modelo",mod)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom == "" && lic != "" && mod == "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("licencia",lic)
                 .whereEqualTo("marca",mar)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom == "" && lic != "" && mod != "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("licencia",lic)
                 .whereEqualTo("modelo",mod)
                 .whereEqualTo("marca",mar)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }
         } else
         if (nom == "" && dom == "" && lic == "" && mod != "" && mar != "") {
             bd.collection("arrendamiento")
                 .whereEqualTo("modelo",mod)
                 .whereEqualTo("marca",mar)
                 .addSnapshotListener { query, error ->
                     if (error != null) {
                         AlertDialog.Builder(requireContext())
                             .setMessage(error.message)
                             .show()
                         return@addSnapshotListener
                     }
                     agregaraListView(query!!)
                 }

         } else {
         AlertDialog.Builder(requireContext())
         .setTitle("Error")
         .setMessage("No se encontraron coincidencias, mostrando todos")
         .show()
             mostrarl()
         }
    }
    fun contexto(op: Context.() ->Unit){
        if(isAdded&&context!=null){
            op(requireContext())

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}