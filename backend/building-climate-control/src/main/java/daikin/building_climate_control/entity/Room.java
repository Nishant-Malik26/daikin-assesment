package daikin.building_climate_control.entity;

import daikin.building_climate_control.util.ACMode;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private float currentTemperature;
    @Enumerated(EnumType.STRING)
    private ACMode acMode;

    public Room() {
        this.currentTemperature =
                Math.round((10 + Math.random() * (40 - 10)) * 10) / 10.0f;
        this.acMode = ACMode.IDLE;
    }

    public void updateMode(float requestedTemp, float temperatureClosenessThreshold) {
        float difference = requestedTemp - currentTemperature;
        if (Math.abs(difference) <= temperatureClosenessThreshold) {
            this.acMode = ACMode.IDLE;
        } else if (difference > 0) {
            this.acMode = ACMode.HEATING;
        } else {
            this.acMode = ACMode.COOLING;
        }
    }

    public void coolingOrHeating(float coolingRate) {
        if (acMode == ACMode.HEATING) {
            currentTemperature = Math.min(40.0f, currentTemperature + coolingRate);
        } else if (acMode == ACMode.COOLING) {
            currentTemperature = Math.max(10.0f, currentTemperature - coolingRate);
        }
        currentTemperature = Math.round(currentTemperature * 10) / 10.0f;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setAcMode(ACMode acMode) {
        this.acMode = acMode;
    }

    public ACMode getAcMode() {
        return acMode;
    }

    public Long getId() {
        return id;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }
}