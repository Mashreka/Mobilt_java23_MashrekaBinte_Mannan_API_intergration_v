# MyTravelApp

MyTravelApp is an Android application that provides weather forecasts, currency exchange rates, and theme customization. The app integrates with external APIs to deliver real-time data.

## Features

- **Weather Forecasts:** Get current weather information for any city using the OpenWeatherMap API.
- **Currency Conversion:** Convert between currencies with real-time rates from the ExchangeRate API.
- **Theme Customization:** Switch between light and dark modes using a toggle switch in the settings.

## APIs Used

1. **OpenWeatherMap API**
   - **Endpoint:** `https://api.openweathermap.org/data/2.5/weather?q={city}&appid={API_KEY}&units=metric`
   - **Purpose:** Fetch current weather data for a specified city.

2. **ExchangeRate API**
   - **Endpoint:** `https://api.exchangerate-api.com/v4/latest/{baseCurrency}`
   - **Purpose:** Retrieve the latest currency exchange rates for conversion.
