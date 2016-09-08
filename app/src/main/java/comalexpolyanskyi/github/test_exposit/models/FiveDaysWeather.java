package comalexpolyanskyi.github.test_exposit.models;

/**
 * Created by Алексей on 08.06.2016.
 */
public class FiveDaysWeather implements Weather {
    private String txt;
    private String description;
    private String icon;
    private double temp;
    private double pressure;
    private int humidity;
    private int speed;
    private int clouds;
    public FiveDaysWeather(String txt, String description, String icon, double temp, double pressure, int humidity, int speed, int clouds) {
        this.txt = txt;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speed = speed;
        this.clouds = clouds;
    }


    public String getPrecipitation() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return speed;
    }

    public String getInfo() {
        return txt;
    }

    public int getCloudsProc() {
        return clouds;
    }
}
