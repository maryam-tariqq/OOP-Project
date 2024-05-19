public class Units {
    private double fahrenheit;
    private double celsius;

    // Constructor that takes the Fahrenheit temperature and converts it to Celsius
    public Units(double fahrenheit) {
        this.fahrenheit = fahrenheit;
        this.celsius = convertFahrenheitToCelsius(fahrenheit);
    }

    // Method to convert Fahrenheit to Celsius
    private double convertFahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    // Getter for Fahrenheit
    public double getFahrenheit() {
        return fahrenheit;
    }

    // Getter for Celsius
    public double getCelsius() {
        return celsius;
    }
}
