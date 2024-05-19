import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class WeatherData {
    private String city;
    private String description;
    public double temperature;
    private double feelsLike;
    private double pressure;

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    private double humidity;
    private double windSpeed;
    private double lon;
    private double lat;

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getLongitude() {
        return lon;
    }

    public double getLatitude() {
        return lat;
    }
    public String getCity(){
        return city;
    }

    public void fetchWeatherData(String city) {
        try {
            this.city = (city);
            String API_KEY = "b0ce38c16aec54e245595450d0c23182";
            String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=b0ce38c16aec54e245595450d0c23182";
            String UNITS = "metric"; // Units in Celsius

            // Constructing the API URL with query parameters
            String urlString = API_URL;
            System.out.println(urlString);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = responseReader.readLine()) != null) {
                    response.append(line);
                }
                responseReader.close();

                JSONObject jsonObject = new JSONObject(response.toString());

                // Extract weather information
                JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
                description = weather.getString("description");


                // Extract coordinates
                JSONObject coord = jsonObject.getJSONObject("coord");
                lon = coord.getDouble("lon");
                lat = coord.getDouble("lat");

                // Extract wind information
                JSONObject wind = jsonObject.getJSONObject("wind");
                windSpeed = wind.getDouble("speed");

                // Extract main weather information
                JSONObject main = jsonObject.getJSONObject("main");
                temperature = main.getDouble("temp");
                temperature -= 270;
                feelsLike = main.getDouble("feels_like");
                feelsLike -= 270;
                pressure = main.getDouble("pressure");
                humidity = main.getDouble("humidity");


            } else {
                System.out.println("Error: Unable to fetch weather data. Response Code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Weather Information:\n" +
                "City: " + city + "\n" +
                "Day: " +
                "Description: " + description + "\n" +
                "Temperature: " + temperature + "°F\n" +
                "Feels Like: " + feelsLike + "°F\n" +
                "Pressure: " + pressure + " hPa\n" +
                "Humidity: " + humidity + "%\n" +
                "Wind Speed: " + windSpeed + " m/s\n" +
                "Longitude: " + lon + "\n" +
                "Latitude: " + lat;
    }}