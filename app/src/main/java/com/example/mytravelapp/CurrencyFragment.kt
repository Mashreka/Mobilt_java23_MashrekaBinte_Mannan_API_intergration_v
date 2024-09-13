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
import com.example.mytravelapp.databinding.FragmentCurrencyBinding
import org.json.JSONException
import org.json.JSONObject

class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fetchButton.setOnClickListener {
            val baseCurrency = binding.baseCurrencyInput.text.toString().uppercase()
            val targetCurrency = binding.targetCurrencyInput.text.toString().uppercase()
            fetchCurrencyData(baseCurrency, targetCurrency)
        }

        binding.buttonOpenWeather.setOnClickListener {
            findNavController().navigate(R.id.action_currencyFragment_to_weatherFragment)
        }

        binding.buttonOpenFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_currencyFragment_to_favoritesFragment)
        }

        binding.buttonOpenSettings.setOnClickListener {
            findNavController().navigate(R.id.action_currencyFragment_to_settingsActivity)
        }
    }

    private fun fetchCurrencyData(baseCurrency: String, targetCurrency: String) {
        val url = "https://api.exchangerate-api.com/v4/latest/$baseCurrency"
        val requestQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val rates = jsonObject.getJSONObject("rates")
                    if (rates.has(targetCurrency)) {
                        val rate = rates.getDouble(targetCurrency)
                        binding.resultText.text = "1 $baseCurrency = $rate $targetCurrency"
                    } else {
                        binding.resultText.text = "Currency $targetCurrency not found."
                    }
                } catch (e: JSONException) {
                    Log.e("API", "JSON Parsing error: ${e.message}")
                    binding.resultText.text = "Error parsing JSON response."
                }
            },
            { error: VolleyError ->
                Log.e("API", "Error: ${error.message}")
                binding.resultText.text = "Error fetching data."
            })
        requestQueue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
