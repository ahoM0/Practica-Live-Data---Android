package com.example.practica_4_livedata_mutablelivedata_mb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practica_4_livedata_mutablelivedata_mb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel : LibrosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuracion vinculacion de datos
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        // Configuramos el adaptador del recyclerview
        binding.recylerview.layoutManager = GridLayoutManager(this, 2)
        binding.recylerview.adapter = viewModel.listado_libros.value?.let {
            LibrosRecyclerViewAdapter(it)
        }

        // Configuracion viewmodel
        binding.alpha = viewModel
        if (binding.recylerview.adapter is LibrosRecyclerViewAdapter) {
            // Metodo para seleccionar el libro del listado
            (binding.recylerview.adapter as LibrosRecyclerViewAdapter).click = { position, libro ->
                run {
                    this.viewModel.setSeleccionado(libro)
                }
            }
            // Observamos los cambios en el listado, cuando se produzca un cambio en ell listado se notifica al adaptador.
            viewModel.listado_libros.observe(this, Observer {
                binding.recylerview.adapter?.notifyDataSetChanged()
            })
        }


        // Listener del boton de modificar o crear libros
        binding.botonAccion.setOnClickListener(){
            this.viewModel.actualizar()
            Log.d("Libro",viewModel.selected.value?.autor+" "+viewModel.selected.value?.titulo)

        }

        // Listener del boton flotante para añadir para vaciar los campos y poder crear un nuevo libro
        binding.addButton.setOnClickListener(){
            viewModel.add_libro()
            Log.d("Libro",viewModel.selected.value?.autor+" "+viewModel.selected.value?.titulo)
        }

        // Listener del boton flotante para borrar Libro
        binding.removeButton.setOnClickListener(){
            viewModel.borrarLibro()
        }
    }
}