package com.example.appfinal // <- VERIFIQUE SE O PACOTE ESTÁ CORRETO

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appfinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ExemploFragment())
                .commit()
        }



        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
        val fragment = when (item.itemId) {
            R.id.navigation_home -> HomeFragment()
            R.id.navigation_dashboard -> DashboardFragment()
            R.id.navigation_notifications -> NotificationFragment()
            else -> null
        }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true

            }

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.navigation_home -> {
                Toast.makeText(this,"Abrindo home", Toast.LENGTH_LONG).show()
                true
            }
            R.id.navigation_dashboard -> {
                Toast.makeText(this,"Abrindo dash", Toast.LENGTH_LONG).show()
                true
            }

            R.id.navigation_notifications -> {
                Toast.makeText(this,"Abrindo notify", Toast.LENGTH_LONG).show()
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    }
