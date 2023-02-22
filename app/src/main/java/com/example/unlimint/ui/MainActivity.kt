package com.example.unlimint.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.unlimint.R
import com.example.unlimint.databinding.ActivityMainBinding
import com.example.unlimint.di.components.sharedpreferences.JokeSharedPref
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val jokeSharedPref: JokeSharedPref by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        if (!jokeSharedPref.getJoke().isNullOrEmpty()) {
            navController.navigate(R.id.SecondFragment)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}


