import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class SecondWindow extends JFrame {
    WeatherData weatherData = new WeatherData();  // Assuming WeatherData is defined elsewhere
    private JLabel cityLabel, descriptionLabel, temperatureLabel, feelsLikeLabel, pressureLabel, humidityLabel, windSpeedLabel, lonLabel, latLabel;
    private JLabel background;
    private ImageIcon backgroundImageIcon;
    private JComboBox<String> unitComboBox;
    private String temperatureUnit = "Celsius";  // Default unit

    public SecondWindow() {
        setTitle("Weather Information");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Change application icon
        try {
            File iconFile = new File("IMG.png"); // Provide path to your icon image file
            Image iconImage = ImageIO.read(iconFile);
            setIconImage(iconImage);
        } catch (IOException e) {
            System.out.println("Error loading icon image: " + e.getMessage());
        }

        background = new JLabel();
        add(background, BorderLayout.CENTER);

        loadAndScaleBackgroundImage(600, 400);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                loadAndScaleBackgroundImage(getWidth(), getHeight());
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(9, 1));
        centerPanel.setOpaque(false);
        initializeLabels(centerPanel);
        background.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        background.add(centerPanel, gbc);

        addFetchWeatherButton();
        addUnitComboBox();
        addWeeklyWeatherButton();
        addRecommendationButton();  // Add recommendation button method call
        setVisible(true);
    }

    private void loadAndScaleBackgroundImage(int width, int height) {
        try {
            File imagePath = new File("image_10.jpg");
            Image originalImage = ImageIO.read(imagePath);
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            backgroundImageIcon = new ImageIcon(scaledImage);
            background.setIcon(backgroundImageIcon);
        } catch (IOException e) {
            System.out.println("Error loading background image: " + e.getMessage());
            background.setText("Background Image Not Found");
        }
    }

    private void addFetchWeatherButton() {
        JButton fetchButton = new JButton("Fetch Weather");
        fetchButton.setFont(new Font("Cascadia Code", Font.BOLD, 18));
        fetchButton.setPreferredSize(new Dimension(200, 40));
        fetchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(fetchButton);
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        background.add(buttonPanel, gbc);

        fetchButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                String city = JOptionPane.showInputDialog(SecondWindow.this, "Enter city name:");
                if (city != null && !city.isEmpty()) {
                    new Thread(() -> {
                        weatherData.fetchWeatherData(city);
                        SwingUtilities.invokeLater(this::displayWeatherInfo);
                    }).start();
                }
            });
        });
    }

    private void addUnitComboBox() {
        String[] units = { "Celsius", "Fahrenheit" };
        unitComboBox = new JComboBox<>(units);
        unitComboBox.setFont(new Font("Cascadia Code", Font.BOLD, 18));
        unitComboBox.setPreferredSize(new Dimension(150, 30));

        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.add(unitComboBox);
        comboBoxPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        background.add(comboBoxPanel, gbc);

        unitComboBox.addActionListener(e -> {
            temperatureUnit = (String) unitComboBox.getSelectedItem();
            displayWeatherInfo();
        });
    }

    private void addWeeklyWeatherButton() {
        JButton weeklyWeatherButton = new JButton("Weekly Weather");
        weeklyWeatherButton.setFont(new Font("Cascadia Code", Font.BOLD, 18));
        weeklyWeatherButton.setPreferredSize(new Dimension(200, 40));
        weeklyWeatherButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(weeklyWeatherButton);
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        background.add(buttonPanel, gbc);

        weeklyWeatherButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                WeeklyWeatherWindow weeklyWindow = new WeeklyWeatherWindow(weatherData, temperatureUnit);
                ImageIcon icon = new ImageIcon("IMG.png"); // Provide path to your icon image file
                weeklyWindow.setIconImage(icon.getImage());
                weeklyWindow.setVisible(true);
            });
        });
    }

    private void addRecommendationButton() {
        JButton recommendationButton = new JButton("Recommendations");
        recommendationButton.setFont(new Font("Cascadia Code", Font.BOLD, 18));
        recommendationButton.setPreferredSize(new Dimension(200, 40));
        recommendationButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel recommendationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        recommendationPanel.add(recommendationButton);
        recommendationPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;  // Positioning below the weekly weather button
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        background.add(recommendationPanel, gbc);

        recommendationButton.addActionListener(e -> {
            Recommendations rec = new Recommendations();
            rec.getRecommendations(weatherData);
            ImageIcon icon = new ImageIcon("IMG.png"); // Provide path to your icon image file
            rec.setIconImage(icon.getImage());
            rec.setVisible(true);

        });
    }

    private void initializeLabels(JPanel panel) {
        Font labelFont = new Font("Cascadia Code", Font.BOLD, 18);
        Color textColor = new Color(0, 0, 139);

        cityLabel = new JLabel("City: ", SwingConstants.CENTER);
        cityLabel.setFont(labelFont);
        cityLabel.setForeground(textColor);
        panel.add(cityLabel);

        JLabel[] labels = {
                descriptionLabel = new JLabel("Description: ", SwingConstants.CENTER),
                temperatureLabel = new JLabel("Temperature: ", SwingConstants.CENTER),
                feelsLikeLabel = new JLabel("Feels Like: ", SwingConstants.CENTER),
                pressureLabel = new JLabel("Pressure: ", SwingConstants.CENTER),
                humidityLabel = new JLabel("Humidity: ", SwingConstants.CENTER),
                windSpeedLabel = new JLabel("Wind Speed: ", SwingConstants.CENTER),
                lonLabel = new JLabel("Longitude: ", SwingConstants.CENTER),
                latLabel = new JLabel("Latitude: ", SwingConstants.CENTER)
        };

        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(textColor);
            panel.add(label);
        }

        weatherData.fetchWeatherData("London");
        displayWeatherInfo();
    }

    private void displayWeatherInfo() {
        cityLabel.setText("City: " + weatherData.getCity());
        descriptionLabel.setText("Description: " + weatherData.getDescription());
        temperatureLabel.setText("Temperature: " + formatTemperature(weatherData.getTemperature()));
        feelsLikeLabel.setText("Feels Like: " + formatTemperature(weatherData.getFeelsLike()));
        pressureLabel.setText("Pressure: " + weatherData.getPressure() + " hPa");
        humidityLabel.setText("Humidity: " + weatherData.getHumidity() + "%");
        windSpeedLabel.setText("Wind Speed: " + weatherData.getWindSpeed() + " m/s");
        lonLabel.setText("Longitude: " + weatherData.getLongitude());
        latLabel.setText("Latitude: " + weatherData.getLatitude());
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
