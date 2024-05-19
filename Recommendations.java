import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Recommendations extends JFrame {
    private WeatherData weatherData;
    private JTextArea recommendationTextArea;

    public Recommendations() {
        setTitle("Weather Recommendations");
        setSize(600, 400); // Increased size for better visibility
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Disable resizing

        weatherData = new WeatherData();

        // Set up the main panel with a light blue background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(173, 216, 230)); // Light blue background
        mainPanel.setLayout(new BorderLayout());

        // Set up the top panel with a label and text field for temperature input
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false); // Make background transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // Add top and bottom margin

        JLabel temperatureLabel = new JLabel("Enter Temperature: ");
        temperatureLabel.setFont(new Font("Cascadia Code", Font.BOLD, 18)); // Set font of the label
        temperatureLabel.setForeground(new Color(0, 0, 139)); // Dark blue color
        topPanel.add(temperatureLabel, gbc);

        gbc.gridy++;
        JTextField temperatureField = new JTextField(10);
        temperatureField.setFont(new Font("Cascadia Code", Font.PLAIN, 18)); // Set font of the text field
        topPanel.add(temperatureField, gbc);

        // Set up the button to fetch recommendations
        gbc.gridy++;
        JButton fetchRecommendationsButton = new JButton("Get Recommendations");
        fetchRecommendationsButton.setFont(new Font("Cascadia Code", Font.BOLD, 18)); // Set font of the button
        fetchRecommendationsButton.setForeground(new Color(0, 0, 139));
        fetchRecommendationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double temperature = Double.parseDouble(temperatureField.getText());
                    weatherData.setTemperature(temperature);
                    getRecommendations(weatherData);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Recommendations.this, "Please enter a valid temperature.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        topPanel.add(fetchRecommendationsButton, gbc);

        // Set up the text area to display recommendations
        recommendationTextArea = new JTextArea();
        recommendationTextArea.setEditable(false);
        recommendationTextArea.setLineWrap(true);
        recommendationTextArea.setWrapStyleWord(true);
        recommendationTextArea.setFont(new Font("Cascadia Code", Font.PLAIN, 18)); // Set font of the text area
        recommendationTextArea.setForeground(new Color(0, 0, 139));

        JScrollPane scrollPane = new JScrollPane(recommendationTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around text area

        // Add components to the main panel
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
    }

    void getRecommendations(WeatherData w) {
        StringBuilder recommendations = new StringBuilder();
        double temperature = w.getTemperature();

        if (temperature < 0) {
            recommendations.append("It's chilly outside. Bundle up in layers to stay warm!\n");
            recommendations.append("Consider wearing a heavy coat, gloves, and a scarf.\n");
        } else if (temperature >= 0 && temperature < 15) {
            recommendations.append("It's cold outside, but it can be nice with the right attire.\n");
            recommendations.append("Wear a warm jacket, hat, and gloves to stay comfortable.\n");
        } else if (temperature >= 15 && temperature <= 35) {
            recommendations.append("The weather is moderate today. Enjoy!\n");
            recommendations.append("Wear comfortable clothes suitable for the temperature.\n");
        } else if (temperature > 35 && temperature <= 45) {
            recommendations.append("It's hot today. Stay hydrated and seek shade when possible!\n");
            recommendations.append("Wear lightweight and breathable clothing.\n");
        } else if (temperature > 45 && temperature < 55) {
            recommendations.append("It's scorching hot outside! Take extreme caution.\n");
            recommendations.append("Avoid outdoor activities if possible.\n");
        } else {
            recommendations.append("The temperature is beyond what's considered normal. Please stay indoors.\n");
            recommendations.append("Use air conditioning or fans to stay cool.\n");
        }

        recommendationTextArea.setText(recommendations.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Recommendations recommendations = new Recommendations();
            recommendations.setVisible(true);
        });
    }
}
