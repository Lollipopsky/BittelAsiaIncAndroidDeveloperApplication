package com.example.weatherapp.Activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.Adapter.ForecastAdapter
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.WeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.model.ForecastResponseApi
import com.github.matteobattilana.weather.PrecipType
import eightbitlab.com.blurview.RenderScriptBlur
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }
    private val forecastAdapter by lazy { ForecastAdapter() }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val geocoder by lazy { Geocoder(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set window properties
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getCurrentLocation()
        }

        // Setup blur view
        setupBlurView()

        // Display current date and time
        getDateTime()
    }

    private fun getDateTime() {
        // Get the current date and time
        val dateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", resources.configuration.locales[0])
        val dateCurrent = dateFormat.format(calendar.time)
        binding.dateTimeTxt.text = dateCurrent // Assuming dateTimeTxt is the ID of your TextView
    }

    private fun loadCurrentWeather(lat: Double, lon: Double) {
        weatherViewModel.loadCurrentWeather(lat, lon, "metric").enqueue(object : Callback<CurrentResponseApi> {
            override fun onResponse(call: Call<CurrentResponseApi>, response: Response<CurrentResponseApi>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        binding.detailLayout.visibility = View.VISIBLE
                        binding.statusTxt.text = data.weather?.get(0)?.main ?: "-"
                        binding.windTxt.text = "${Math.round(data.wind?.speed ?: 0.0)} Km/h"
                        binding.humidityTxt.text = "${data.main?.humidity ?: 0}%"
                        binding.currentTempTxt.text = "${Math.round(data.main?.temp ?: 0.0)}°C ${Math.round((data.main?.temp ?: 0.0) * 9 / 5 + 32)}°F"
                        binding.maxTempTxt.text = "${Math.round(data.main?.tempMax ?: 0.0)}°C"
                        binding.minTempTxt.text = "${Math.round(data.main?.tempMin ?: 0.0)}°C"

                        val drawable = if (isNightNow()) R.drawable.night_bg else setDynamicallyWallpaper(data.weather?.get(0)?.icon ?: "-")
                        binding.bgImage.setImageResource(drawable)
                        setEffectRainSnow(data.weather?.get(0)?.icon ?: "-")
                    }
                } else {
                    showToast("Failed to load current weather: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                showToast(t.toString())
            }
        })
    }

    private fun loadForecastWeather(lat: Double, lon: Double) {
        weatherViewModel.loadForecastWeather(lat, lon, "metric").enqueue(object : Callback<ForecastResponseApi> {
            override fun onResponse(call: Call<ForecastResponseApi>, response: Response<ForecastResponseApi>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        binding.blurView.visibility = View.VISIBLE
                        forecastAdapter.differ.submitList(data.list)
                        binding.forecastView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        binding.forecastView.adapter = forecastAdapter
                    }
                } else {
                    Log.e("API Error", "Error code: ${response.code()}, message: ${response.message()}")
                    binding.blurView.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ForecastResponseApi>, t: Throwable) {
                binding.blurView.visibility = View.GONE
                showToast("Failed to load forecast data: ${t.message}")
            }
        })
    }

    private fun setupBlurView() {
        val radius = 10f
        val decorView = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground = decorView.background

        rootView?.let {
            binding.blurView.setupWith(it, RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius)
            binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            binding.blurView.clipToOutline = true
        }
    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }

    private fun setDynamicallyWallpaper(icon: String): Int {
        return when (icon.dropLast(1)) {
            "01" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.snow_bg
            }
            "02", "03", "04" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.cloudy_bg
            }
            "09", "10", "11" -> {
                initWeatherView(PrecipType.RAIN)
                R.drawable.rainy_bg
            }
            "13" -> {
                initWeatherView(PrecipType.SNOW)
                R.drawable.snow_bg
            }
            "50" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.haze_bg
            }
            else -> 0
        }
    }

    private fun setEffectRainSnow(icon: String) {
        when (icon.dropLast(1)) {
            "01", "02", "03", "04" -> initWeatherView(PrecipType.CLEAR)
            "09", "10", "11" -> initWeatherView(PrecipType.RAIN)
            "13" -> initWeatherView(PrecipType.SNOW)
            "50" -> initWeatherView(PrecipType.CLEAR)
        }
    }

    private fun initWeatherView(type: PrecipType) {
        binding.weatherView.apply {
            setWeatherData(type)
            angle = -20
            emissionRate = 100.0f
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    displayLocation(lat, lon)
                    // Load weather data using the current location
                    loadCurrentWeather(lat, lon)
                    loadForecastWeather(lat, lon)
                } else {
                    showToast("Unable to get location")
                }
            }.addOnFailureListener {
                showToast("Failed to retrieve location: ${it.message}")
            }
        } else {
            showToast("Location permission not granted.")
        }
    }

    private fun displayLocation(lat: Double, lon: Double) {
        val addresses = geocoder.getFromLocation(lat, lon, 1) ?: return
        if (addresses.isNotEmpty()) {
            val cityName = addresses[0].locality ?: "Unknown City"
            binding.cityTxt.text = "$cityName, Philippines"
        } else {
            binding.cityTxt.text = "Location not found"
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
