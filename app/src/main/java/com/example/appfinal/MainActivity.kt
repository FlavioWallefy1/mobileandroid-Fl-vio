package com.example.appfinal // <- VERIFIQUE SE O PACOTE ESTÁ CORRETO

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appfinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ExemploFragment())
                .commit()
        }

        binding.button1.setOnClickListener {
            sharedViewModel.enviarMensagem("Botão 1 foi clicado!")
        }

        binding.button2.setOnClickListener {
            sharedViewModel.enviarMensagem("Agora foi o Botão 2!")
        }

        binding.button3.setOnClickListener {
            sharedViewModel.enviarMensagem("Por último, o Botão 3!")
        }
    }
}