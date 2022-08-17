package com.cities.weatherapp.ui.citylisting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cities.weatherapp.R
import com.cities.weatherapp.ui.citylisting.model.CityWeatherModel


class CityListAdapter
internal constructor(
    passedUrlList: ArrayList<CityWeatherModel>, onAdapterClickListener: CitySelectListener
) : RecyclerView.Adapter<CityListAdapter.HistoryItemViewHolder>() {


    private var urlList = ArrayList<CityWeatherModel>()
    private var classScopedItemClickListener: CitySelectListener = onAdapterClickListener

    init {
        this.urlList = passedUrlList
        this.classScopedItemClickListener = onAdapterClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val holder: HistoryItemViewHolder
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        holder = HistoryItemViewHolder(inflater.inflate(R.layout.inner_item, parent, false))
        return holder

    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        val cityDetail = urlList[position]
        holder.cityDetailTextView.text = cityDetail.cityName +", " + cityDetail.countryName
        holder.updateWeatherButton.setOnClickListener {
            classScopedItemClickListener.displayUpdatedWeatherOfSelectedCity(position)
        }
    }

    override fun getItemCount(): Int = urlList.size

    inner class HistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var cityDetailTextView: TextView = itemView.findViewById(R.id.city_detail_textview)
        var updateWeatherButton: Button = itemView.findViewById(R.id.check_btn)


        override fun onClick(v: View?) {}
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}