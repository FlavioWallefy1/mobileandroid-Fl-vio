package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> get() = _mensagem

    fun enviarMensagem(texto: String) {
        _mensagem.value = texto
    }
}