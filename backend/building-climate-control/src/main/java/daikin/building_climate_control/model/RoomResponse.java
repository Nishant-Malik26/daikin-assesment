package daikin.building_climate_control.model;

public class RoomResponse {
    private Long id;
    private float currentTemperature;
    private String temperatureUnit;
    private String acMode;

    public RoomResponse(Long id, float currentTemperature, String acMode) {
        this.id = id;
        this.currentTemperature = currentTemperature;
        this.acMode = acMode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void setAcMode(String acMode) {
        this.acMode = acMode;
    }

    public Long getId() {
        return id;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getAcMode() {
        return acMode;
    }
}
