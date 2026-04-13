package daikin.building_climate_control.tests;

import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.util.ACMode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomEntityTest {
    @Test
    void shouldSetHeatingWhenTemperatureIsBelowRequested() {
        Apartment apartment = new Apartment();
        apartment.setCurrentTemperature(18.0f);

        apartment.updateMode(22.0f, 0.5f);
        assertEquals(ACMode.HEATING, apartment.getAcMode());
    }

    @Test
    void shouldSetCoolingWhenTemperatureIsAboveRequested(){
        Apartment apartment = new Apartment();
        apartment.setCurrentTemperature(24.0f);

        apartment.updateMode(22.0f, 0.5f);
        assertEquals(ACMode.COOLING, apartment.getAcMode());
    }

    @Test
    void shouldSetIdleWhenTemperatureIsCloseInRange(){
        Apartment apartment = new Apartment();
        apartment.setCurrentTemperature(22.3f);

        apartment.updateMode(22.0f, 0.5f);
        assertEquals(ACMode.IDLE, apartment.getAcMode());
    }

    @Test
    void shouldIncreaseTemperatureWhenHeating(){
        Apartment apartment = new Apartment();
        apartment.setCurrentTemperature(18.0f);

        apartment.setAcMode(ACMode.HEATING);
        apartment.coolingOrHeating(0.2f);
        assertEquals(18.2f, apartment.getCurrentTemperature());
    }

    @Test
    void shouldDecreaseTemperatureWhenCooling(){
        Apartment apartment = new Apartment();
        apartment.setCurrentTemperature(18.0f);

        apartment.setAcMode(ACMode.COOLING);
        apartment.coolingOrHeating(0.2f);
        assertEquals(17.8f, apartment.getCurrentTemperature());
    }
}
