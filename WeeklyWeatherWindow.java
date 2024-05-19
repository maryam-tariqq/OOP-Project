import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class WeeklyWeatherWindow extends JFrame {
    private WeatherData weatherData;
    private String temperatureUnit;

    public WeeklyWeatherWindow(WeatherData weatherData, String temperatureUnit) {
        this.weatherData = weatherData;
        this.temperatureUnit = temperatureUnit;

        setTitle("Weekly Weather Forecast");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        // Set background color to light blue
        getContentPane().setBackground(new Color(173, 216, 230));

        initializeLabels();
    }

    private void initializeLabels() {
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate forecastDate = currentDate.plusDays(i);
            String dayOfWeek = forecastDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            double dailyTemp = (i == 0) ? weatherData.getTemperature() : weatherData.getTemperature() + (i - 3); // No variation for current day
            JLabel dailyLabel = new JLabel(dayOfWeek + ": " + formatTemperature(dailyTemp), SwingConstants.CENTER);
            dailyLabel.setFont(new Font("Cascadia Code", Font.BOLD, 18));
            dailyLabel.setForeground(new Color(0, 0, 139));  // Dark blue color
            dailyLabel.setOpaque(true);  // Ensure the label's background is drawn
            dailyLabel.setBackground(new Color(173, 216, 230));  // Match the light blue background
            add(dailyLabel);
        }
    }

    private String formatTemperature(double temperatureInCelsius) {
        if ("Fahrenheit".equals(temperatureUnit)) {
            double temperatureInFahrenheit = temperatureInCelsius * 9 / 5 + 32;
            return String.format("%.2f°F", temperatureInFahrenheit);
        } else {
            return String.format("%.2f°C", temperatureInCelsius);
        }
    }


}
