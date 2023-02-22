package com.example.unlimint.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unlimint.R
import com.example.unlimint.adapter.MainAdapter
import com.example.unlimint.databinding.FragmentJokeBinding
import com.example.unlimint.di.components.sharedpreferences.JokeSharedPref
import com.example.unlimint.extensions.gone
import com.example.unlimint.extensions.visible
import com.example.unlimint.network.utils.Status
import com.example.unlimint.utils.FixedQeue
import com.example.unlimint.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SecondFragment : Fragment() {

    private var _binding: FragmentJokeBinding? = null

    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModel()
    private val jokesList = FixedQeue<String>(10)
    private val jokeSharedPref: JokeSharedPref by inject()

    private val mainAdapter by lazy {
        MainAdapter(jokesList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentJokeBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        setupObserver()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(binding.recyclerView.context, (binding.recyclerView.layoutManager as LinearLayoutManager).orientation))
            this.adapter = mainAdapter
        }
        binding.btLogout.setOnClickListener {
            mainViewModel.stopJob()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun setupObserver() {
        readList()
        mainViewModel.joke.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.gone()
                    it.data?.let {
                        jokesList.add(it)
                        mainAdapter.notifyDataSetChanged()
                    }
                    binding.recyclerView.visible()
                }
                Status.LOADING -> {
                    binding.progressBar.visible()
                    binding.recyclerView.gone()
                }
                Status.ERROR -> {
                    binding.progressBar.gone()
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun readList() {
        if (!jokeSharedPref.getJoke().isNullOrEmpty()) {
            jokeSharedPref.getJoke().forEach {
                jokesList.add(it)
            }
        }
    }
}