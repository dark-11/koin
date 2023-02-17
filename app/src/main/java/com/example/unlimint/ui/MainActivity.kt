package com.example.unlimint.ui


import com.example.unlimint.utils.FixedQeue
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unlimint.R
import com.example.unlimint.adapter.MainAdapter
import com.example.unlimint.databinding.ActivityMainBinding
import com.example.unlimint.di.components.sharedpreferences.JokeSharedPref
import com.example.unlimint.extensions.gone
import com.example.unlimint.extensions.visible
import com.example.unlimint.network.utils.Status
import com.example.unlimint.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val jokesList = FixedQeue<String>(10)

    private val jokeSharedPref: JokeSharedPref by inject()

    private val mainAdapter by lazy {
        MainAdapter(jokesList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupUI()
        setContentView(binding.root)
        binding.etPhone.doAfterTextChanged {
            if (it?.length == 10) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (inputMethodManager.isAcceptingText)
                    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, /*flags:*/0)
                binding.etPhone.gone()
                binding.recyclerView.visible()
                binding.progressBar.gone()
                jokesList.clear()
                mainViewModel.startJob()
                readList()
            }
        }
        setSupportActionBar(binding.myToolbar)
    }
    private fun setupUI() {
        if (jokeSharedPref.getJoke().isNullOrEmpty()) {
            binding.etPhone.visible()
        } else {
            setupObserver()
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(binding.recyclerView.context, (binding.recyclerView.layoutManager as LinearLayoutManager).orientation))
            this.adapter = mainAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_settings) {
            binding.etPhone.text = null
            binding.etPhone.visible()
            binding.progressBar.gone()
            binding.recyclerView.gone()
            mainViewModel.stopRepeatingJoke()
            return true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupObserver() {
        readList()
        mainViewModel.joke.observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.gone()
                        binding.etPhone.gone()
                        it.data?.let {
                            jokesList.add(it)
                            mainAdapter.notifyDataSetChanged()
                        }
                        binding.recyclerView.visible()
                    }
                    Status.LOADING -> {
                        binding.etPhone.gone()
                        binding.progressBar.visible()
                        binding.recyclerView.gone()
                    }
                    Status.ERROR -> {
                        binding.etPhone.visible()
                        binding.progressBar.gone()
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun readList() {
        if (!jokeSharedPref.getJoke().isNullOrEmpty()) {
            jokeSharedPref.getJoke().forEach {
                jokesList.add(it)
            }
            mainAdapter.notifyDataSetChanged()
        }
    }
}