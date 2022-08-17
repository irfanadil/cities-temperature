package com.cities.weatherapp.ui.city

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cities.weatherapp.R
import com.cities.weatherapp.databinding.FragmentHomeBinding
import com.cities.weatherapp.extensions.hideKeyboard
import com.cities.weatherapp.extensions.showToast
import com.cities.weatherapp.ui.MainViewModel
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CitySelectionFragment : Fragment() , PlacePredictionAdapter.OnPlaceClickListener  {

    @Inject
    lateinit var googlePlacesClass: GooglePlacesClass

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var predictAdapter: PlacePredictionAdapter
    private var googleSuggestedSelected:AutocompletePrediction? = null

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupUI()
        setUpGooglePlaces()
        //Timber.e(" onCreateView again and again....")

        binding.button.setOnClickListener{
            googleSuggestedSelected?.let {
                val citySelected = it.getPrimaryText(null).toString()
                val countrySelected = it.getSecondaryText(null).toString()
                if (citySelected.isNotBlank() && citySelected.isNotEmpty() && countrySelected.isNotEmpty() && countrySelected.isNotBlank()) {
                    if (viewModel.saveCityToLocalList(citySelected, countrySelected))
                        findNavController().navigate(R.id.navigation_city_list_screen)
                    else
                        requireContext().showToast("City name already added...")
                }
            }
        }
        return root
    }


    private fun setupUI(){
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        predictAdapter = PlacePredictionAdapter(this@CitySelectionFragment)
        binding.fakePlaceSearchRecyclerView.apply {
            this.layoutManager = linearLayoutManager
            this.setHasFixedSize(true)
            predictAdapter = PlacePredictionAdapter(this@CitySelectionFragment)
            this.adapter = predictAdapter
        }
        binding.cityTextView.addTextChangedListener(googlePlacesClass.placeTextWatcher)
    }

    // Using LiveData to listen changes...
    private fun setUpGooglePlaces(){
        googlePlacesClass.placesBoolean().observe(viewLifecycleOwner) {
            if (it) {
                binding.fakePlaceSearchRecyclerView.visibility = View.VISIBLE
                Timber.e(" showing again and again....")
            } else
                hidePlaceRecycleView()
        }
        googlePlacesClass.autoCompletePredicationList.observe(viewLifecycleOwner) {
            predictAdapter.setPredictions(it)
        }
    }

    override fun onPlaceSelectedByUser(place: AutocompletePrediction) {
        googleSuggestedSelected = place
        binding.cityTextView.setText(place.getFullText(null).toString())
        binding.cityTextView.clearFocus()
        //binding.cityTextView.removeTextChangedListener(googlePlacesClass.placeTextWatcher)
        hidePlaceRecycleView()
        hideKeyboard()
    }

    private fun hidePlaceRecycleView(){
        binding.fakePlaceSearchRecyclerView.visibility = View.GONE
        predictAdapter.setPredictions(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}