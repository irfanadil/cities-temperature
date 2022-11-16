package com.cities.weatherapp.ui.city


import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cities.weatherapp.ui.SingleLiveEvent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient



class GooglePlacesClass(private val appContext:Context) {

    private var predicationList: ArrayList<String> = ArrayList()
    private var token: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()
    private var placesClient: PlacesClient

    private val _autoCompletePredicationList = MutableLiveData< List<AutocompletePrediction>>()
    val autoCompletePredicationList: LiveData< List<AutocompletePrediction>> = _autoCompletePredicationList

    init {
        token = AutocompleteSessionToken.newInstance()
        Places.initialize(appContext, "AIzaSyBzPEiQOTReBzy6W1UcIyHApPu7_5Die6w")
        placesClient = Places.createClient(appContext)

    }

    private fun searchForGooglePlaces(queryPlace: String) {

        // Use the builder to create a FindAutocompletePredictionsRequest.
        val request =
            FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                //.setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(LatLng(37.0902, 95.7129))
                //s.setCountries("USA")
                .setTypeFilter(TypeFilter.CITIES)
                .setSessionToken(token)
                .setQuery(queryPlace)
                .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                for (prediction in response.autocompletePredictions) {
                    //Log.e(TAG, prediction.placeId)
                    response.autocompletePredictions
                    predicationList.add(prediction.getFullText(null).toString())
                    // Log.e(TAG, prediction.getFullText(null).toString())
                }
                //predictAdapter.setPredictions(response.autocompletePredictions)
                _autoCompletePredicationList.value = response.autocompletePredictions

            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    //Log.e(TAG, "Place not found: " + exception.statusCode)
                }
            }
    }



     val placeTextWatcher = (object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {

            val str: String = s.toString()
            if (str.length >= 3) {
                //_showHideGooglePlacesRecyclerView.value = true
                showHidePlaces.value = true
                searchForGooglePlaces(str)
            } else
                if (str.length in 0..2) {
                    //_showHideGooglePlacesRecyclerView.value = false
                    showHidePlaces.value = false
                }
        }
    })

    private val showHidePlaces = SingleLiveEvent<Boolean>()

    fun placesBoolean(): SingleLiveEvent<Boolean> {
        return showHidePlaces
    }

}