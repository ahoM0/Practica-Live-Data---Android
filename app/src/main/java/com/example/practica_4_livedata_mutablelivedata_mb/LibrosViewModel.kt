package com.example.practica_4_livedata_mutablelivedata_mb

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LibrosViewModel : ViewModel() {
    // Booleana define si el libro es uno nuevo o es uno que ya existe.
    var nuevo_libro : Boolean = true
    // Variable privada del listado de libros mutable.
    private var _listado_libros : MutableLiveData <MutableList<Libro>> = MutableLiveData(mutableListOf())
    // Variable publica de lista libros que se observa
    val listado_libros : LiveData <MutableList<Libro>>
        get() = _listado_libros
    // Variable privada que establece el libro seleccionado
    private var _selected = MutableLiveData<Libro>(Libro("","","", ""))
    // variable publica del libro que esta conectada a los campos.
    var selected = MutableLiveData<Libro>(Libro("","","", ""))

    //Inicializamos el listdo de libros
    init {
        this._listado_libros.value?.add(Libro("El Oscuro Río","Ronald Araujo","FC Barnitel","01-21-2002"))
        this._listado_libros.value?.add(Libro("Ganas y canas","Romeo Santos","El climax","12-02-2014"))
        this._listado_libros.value?.add(Libro("Rascando y llorando","Kid Kheo","Socavon Soterrado","21-06-2022"))
        this._listado_libros.value?.add(Libro("EL fenix ilusionista","Sergio el Ferrer","TeamTactics","10-10-2005"))
        this._listado_libros.value?.add(Libro("Record Mortal","Angelo","FluoStep","12-06-1999"))
        this._listado_libros.value?.add(Libro("Animos Rosas","Frid Gone","Caserio Colesterol","05-02-2018"))
    }

    // Funcion que limpia los campos para añadir un nuevo libro
    public fun add_libro(){
        // Definimos como seleccionado el libro vacio.
        this._selected.value = Libro("","","","")
        this.nuevo_libro = true
        // Y definimos los campos de la interfaz con el libro vacio.
        this.selected.value = this._selected.value

    }

    // Funcion que borra el libro seleccionado
    fun borrarLibro(){
        // Comprobamos qque borraremos un libro con datos
        if (!nuevo_libro){
                this.selected.value?.let {
                    this._listado_libros.value?.remove(it)
                    this.actualizarListado()
                }
                // Lo borramos y seleccionamos como libro nada.
                this._selected.value = Libro("","","","")
                this.selected.value = this._selected.value
                this.nuevo_libro = true
        }

    }

    // Funcion actualizar listado y crear un libro nuevo
    public fun actualizar(){
        // Comprobamos que este completos los datos introducidos estan completos antes dde hacer nada
        if(libroCompleto(this.selected.value)) {
            // Si los datos estan completos comprobamos si es nuevo o estan modificandolo
            if (nuevo_libro) {
                // Si es nuevo lo añadimos al listado
                this.selected.value?.let {
                    this._listado_libros.value?.add(it)
                    // Actyualizamos el listado
                    this.actualizarListado()
                }
                this._selected.value = Libro("", "", "", "")
                this.selected.value = this._selected.value
                this.nuevo_libro = false

            } else {
                // Si el libro no es nuevo modificamos el seleccionado por los datos introducidos que estan conectados con la variable selected publica
                this._selected.value?.let {
                    it.autor = selected.value?.let { it.autor } ?: it.autor
                    it.titulo = selected.value?.let { it.titulo } ?: it.titulo
                    it.editorial = selected.value?.let { it.editorial } ?: it.editorial
                    it.anyo = selected.value?.let { it.anyo } ?: it.anyo
                    // Y actualizamos el listado y el libro
                    this.actualizarListado()
                    this.actualizarLibro()
                }
            }
        }
    }

    // Funcion comprueba que el libro tenga los datos completos
    fun libroCompleto(libro: Libro?): Boolean {
        return libro!!.titulo.isNotBlank() && libro.autor.isNotBlank() && libro.anyo.isNotBlank() && libro.editorial.isNotBlank()
    }

    // Define como seleccionado el libro que le pasemos, en este caso con el boton ver del listado.
    fun setSeleccionado(item: Libro) {
        this._selected.value = item
        this.selected.value = item.copy()
        this.nuevo_libro=false
    }

    // Funcion actualizar listado de libros.
    private fun actualizarListado(){
        var l_bros = this._listado_libros.value
        this._listado_libros.value = l_bros
    }

    // Funcion actualizar un libro
    private fun actualizarLibro(){
        this.selected.value = this._selected.value?.copy()
    }


}