package com.cities.weatherapp.ui.weather

import android.annotation.SuppressLint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.cities.weatherapp.api.GenericApiResponse
import com.cities.weatherapp.databinding.FragmentNotificationsBinding
import com.cities.weatherapp.extensions.showToast
import com.cities.weatherapp.ui.MainViewModel
import timber.log.Timber


class WeatherFragment : Fragment() {


    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observeCityWeather()
        return root
    }


    @SuppressLint("SetTextI18n")
    private fun observeCityWeather(){

        viewModel.weatherResponseModel.observe(viewLifecycleOwner){
            when(it){
                is GenericApiResponse.Success ->{
                    val result =  it.value

                    binding.cityCelsius.text = result.location.name+" temperature in Celsius is "+ result.current.temp_c
                    binding.cityFahrenheit.text =  result.location.name+" temperature in fahrenheit is "+ result.current.temp_f
                    val imageUrl = "https:"+result.current.condition.icon
                    Timber.e("imageUrl is $imageUrl")
                    Glide.with(this)
                        .load(imageUrl)
                        .fitCenter()
                        .into(binding.weatherImageView)

                }
                is GenericApiResponse.Failure ->{
                    requireContext().showToast("Api Failure, Reason is = "+it.message)
                }
                else -> {
                    requireContext().showToast("Unknown Failure...")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}