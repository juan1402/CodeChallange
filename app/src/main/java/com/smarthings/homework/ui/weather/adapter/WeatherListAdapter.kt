package com.smarthings.homework.ui.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smarthings.domain.model.DailyInfo
import com.smarthings.homework.R
import com.smarthings.homework.databinding.WeatherItemLayoutBinding
import com.smarthings.homework.extensions.toReadableDate
import com.smarthings.homework.extensions.basicDiffUtil
import com.smarthings.homework.extensions.load
import com.smarthings.homework.extensions.kelvinsToCelsius
import com.smarthings.homework.extensions.kelvinsToFahrenheit
import java.util.Locale

class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    private var weatherInfo: List<DailyInfo> by basicDiffUtil()
    private var isInCelsius = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(WeatherItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(weatherInfo[position])
    }

    override fun getItemCount() = weatherInfo.size

    fun update(weatherInfo: List<DailyInfo>) {
        this.weatherInfo = weatherInfo
    }

    fun temperatureMode(isInCelsius: Boolean) {
        this.isInCelsius = isInCelsius
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: WeatherItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(info: DailyInfo) = with(binding) {
            val context = root.context
            date.text = info.timeStamp.toReadableDate()
            iconImage.load(info.icon)

            mainWeather.text = String.format(
                Locale.getDefault(),
                context.getString(R.string.mainly_temp),
                info.mainly
            )
            maxTemperature.text = String.format(
                Locale.getDefault(),
                if (isInCelsius) context.getString(R.string.max_temp_celsius)
                else context.getString(R.string.max_temp_fahrenheit),
                if (isInCelsius) info.maximum.kelvinsToCelsius()
                else info.maximum.kelvinsToFahrenheit()
            )
            minTemperature.text = String.format(
                Locale.getDefault(),
                if (isInCelsius) context.getString(R.string.min_temp_celsius)
                else context.getString(R.string.min_temp_fahrenheit),
                if (isInCelsius) info.minimum.kelvinsToCelsius()
                else info.minimum.kelvinsToFahrenheit()
            )
        }
    }
}