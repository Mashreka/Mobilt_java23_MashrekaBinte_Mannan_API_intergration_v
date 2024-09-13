package com.example.mytravelapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mytravelapp.databinding.FragmentWeatherBinding
import org.json.JSONObject

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fetchButton.setOnClickListener {
            val city = binding.cityInput.text.toString()
            fetchWeatherData(city)
        }

        binding.buttonOpenCurrency.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_currencyFragment)
        }

        binding.buttonOpenFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_favoritesFragment)
        }

        binding.buttonOpenSettings.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_settingsActivity)
        }
    }

    private fun fetchWeatherData(city: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=*****&units=metric"
        val requestQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                val weather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description")
                val temp = jsonObject.getJSONObject("main").getDouble("temp")
                binding.resultText.text = "Weather in $city: $weather, Temperature: $temp°C"
            },
            { error: VolleyError ->
                Log.e("API", "Error: ${error.message}")
            })
        requestQueue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
