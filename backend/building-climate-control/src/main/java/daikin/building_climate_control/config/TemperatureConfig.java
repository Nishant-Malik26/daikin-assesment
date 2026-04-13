package daikin.building_climate_control.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "temperature")
public class TemperatureConfig {

    private float threshold;
    private float coolingRate;

    public float getThreshold() {
        return threshold;
    }

    public float getCoolingRate() {
        return coolingRate;
    }

    public void setCoolingRate(float coolingRate) {
        this.coolingRate = coolingRate;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

}
