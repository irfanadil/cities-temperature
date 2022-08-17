package com.cities.weatherapp.ui.city

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cities.weatherapp.R
import com.google.android.libraries.places.api.model.AutocompletePrediction
import java.util.*

/**
 * A [RecyclerView.Adapter] for a [com.google.android.libraries.places.api.model.AutocompletePrediction].
 */
class PlacePredictionAdapter( private val onPlaceClickListener: OnPlaceClickListener) : RecyclerView.Adapter<PlacePredictionAdapter.PlacePredictionViewHolder>() {
    private val predictions: MutableList<AutocompletePrediction> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacePredictionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacePredictionViewHolder(
            inflater.inflate(R.layout.place_prediction_item, parent, false))
    }

    override fun onBindViewHolder(holder: PlacePredictionViewHolder, position: Int) {
        val place = predictions[position]
        holder.setPrediction(place)
        holder.itemView.setOnClickListener {
            onPlaceClickListener.onPlaceSelectedByUser(place)
        }
    }

    override fun getItemCount(): Int = predictions.size

    class PlacePredictionViewHolder(itemView: View) : ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.text_view_title)
        //private val address: TextView = itemView.findViewById(R.id.text_view_address)

        fun setPrediction(prediction: AutocompletePrediction) {
            title.text = prediction.getFullText(null)
            //address.text = prediction.getSecondaryText(null)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPredictions(mPlaces: List<AutocompletePrediction>?) {
        this.predictions.clear()
        if(mPlaces!=null)
            this.predictions.addAll(mPlaces)
        notifyDataSetChanged()
    }

    interface OnPlaceClickListener {
        fun onPlaceSelectedByUser(place: AutocompletePrediction)
    }
}