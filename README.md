# CodeChallange

Queries are made to https://api.openweathermap.org/data/2.5/onecall 
To search weather information for a city

1. Ener a city / country name on the edit text
2. Click on search ! (Android Geocoder is used to get Address data for this entry)
3. https://api.openweathermap.org/data/2.5/onecall with Geocoders Latitiude and Longitude
4. Response is displayed on a recycler view (Room is added to cache responses)

# Libraries Used
1. Glide for image loading
2. Mockito for testing
3. Koin for DI,
4. Retrofit for network calls
5. Room
6. Architecture 
7.coroutines
