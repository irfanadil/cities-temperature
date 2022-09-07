package com.cities.weatherapp.ui.citylisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cities.weatherapp.R
import com.cities.weatherapp.databinding.FragmentDashboardBinding

import com.cities.weatherapp.ui.MainViewModel
import com.cities.weatherapp.ui.citylisting.model.CityWeatherModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityListingFragment : Fragment() , CitySelectListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private var citiesList: ArrayList<CityWeatherModel> = ArrayList()
    private lateinit var cityListAdapter: CityListAdapter
    private lateinit var linearLayoutManager:LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupUI()
        return root
    }

    private fun setupUI(){
        citiesList = viewModel.getCitiesWeatherList()
        binding.cityRecyclerView.apply {
            linearLayoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL, false)
            this.layoutManager = linearLayoutManager
            this.setHasFixedSize(true)
            cityListAdapter = CityListAdapter(citiesList, this@CityListingFragment)
            this.adapter = cityListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayUpdatedWeatherOfSelectedCity(position: Int) {
        viewModel.getCityWeatherAndImage(citiesList[position].cityName)
        findNavController().navigate(R.id.action_navigation_city_list_screen_to_navigation_weather_screen2)
    }
}

