package com.example.bikeapitest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bikeapitest.adapter.BikesApiAdapter
import com.example.bikeapitest.databinding.FragmentBikesBinding
import com.example.bikeapitest.model.Feature
import com.example.bikeapitest.viewmodel.BikeApiViewModel
import com.example.bikeapitest.extensions.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class BikesFragment : Fragment(), BikesApiAdapter.OpenMapClickListener {

    private lateinit var binding: FragmentBikesBinding
    private val viewModel: BikeApiViewModel by viewModel()
    private lateinit var adapter: BikesApiAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBikesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BikesApiAdapter(arrayListOf(), this)
        binding.recyclerView.adapter = adapter

        viewModel.observeBikeApi.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { fetchApi ->
                        renderList(fetchApi)
                    }
                    binding.recyclerView.isVisible = true
                }
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerView.isVisible = false
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    getCachedData()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderList(fetchApi: List<Feature>) {
        adapter.addData(fetchApi)
        adapter.submitList(fetchApi)
    }

    private fun getCachedData() {
        viewModel.getCachedApi.observe(viewLifecycleOwner) {
            renderList(it)
        }
    }

    override fun openMapClickListener(feature: Feature) {
        findNavController().navigate(BikesFragmentDirections.actionBikesFragmentToMapsFragment(feature))
    }

    companion object {

        fun newInstance() =
            BikesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
