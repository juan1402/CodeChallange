package com.smarthings.homework.ui.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smarthings.domain.model.WeatherInfo
import com.smarthings.homework.databinding.ActivityWeatherBinding
import com.smarthings.homework.extensions.gone
import com.smarthings.homework.extensions.snackBar
import com.smarthings.homework.extensions.subscribe
import com.smarthings.homework.extensions.visible
import com.smarthings.homework.ui.base.Error
import com.smarthings.homework.ui.base.Loading
import com.smarthings.homework.ui.base.Success
import com.smarthings.homework.ui.base.ViewState
import com.smarthings.homework.ui.weather.adapter.WeatherListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var binding: ActivityWeatherBinding
    private val adapter = WeatherListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeToData()
        initialize()
    }

    private fun initialize() = with(binding) {
        weatherInfoList.adapter = adapter
        searchButton.setOnClickListener {
            viewModel.searchButtonAction(
                binding.cityEditText.text.toString()
            )
        }
        tempSwitch.setOnCheckedChangeListener { _, isChecked ->
            adapter.temperatureMode(isChecked)
        }
    }

    private fun subscribeToData() {
        viewModel.viewState.subscribe(this, ::handleViewState)
    }

    private fun handleViewState(viewState: ViewState<WeatherInfo>) {
        when (viewState) {
            is Loading -> binding.progressBar.visible()
            is Success -> showWeatherData(viewState.data)
            is Error -> handleError(viewState.error.localizedMessage)
        }
    }

    private fun showWeatherData(data: WeatherInfo) {
        adapter.update(data.dailyInfo)
        binding.progressBar.gone()
    }

    private fun handleError(error: String?) {
        snackBar(error ?: String(), binding.root)
        binding.progressBar.gone()
    }
}