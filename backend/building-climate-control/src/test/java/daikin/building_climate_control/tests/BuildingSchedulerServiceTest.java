package daikin.building_climate_control.tests;

import daikin.building_climate_control.config.TemperatureConfig;
import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.repository.BuildingRepository;
import daikin.building_climate_control.service.BuildingSchedulerService;
import daikin.building_climate_control.util.ACMode;
import daikin.building_climate_control.util.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuildingSchedulerServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private TemperatureConfig temperatureConfig;

    private BuildingSchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        schedulerService = new BuildingSchedulerService(buildingRepository, temperatureConfig);
    }

    @Test
    void shouldRecalculateApartmentAndCommonRoomTemperatures() {
        Building building = new Building();
        building.setRequestedTemperature(22.0f);

        Apartment apartment = new Apartment();
        apartment.setCurrentTemperature(18.0f);
        apartment.setAcMode(ACMode.HEATING);
        building.addApartment(apartment);

        CommonRoom room = new CommonRoom();
        room.setRoomType(RoomType.GYM);
        room.setCurrentTemperature(25.0f);
        room.setAcMode(ACMode.COOLING);
        building.addRoom(room);

        when(buildingRepository.findAll()).thenReturn(List.of(building));
        when(temperatureConfig.getThreshold()).thenReturn(0.5f);
        when(temperatureConfig.getCoolingRate()).thenReturn(0.2f);

        schedulerService.recalculateBuildingClimate();

        assertTrue(apartment.getCurrentTemperature() > 18.0f);
        assertTrue(room.getCurrentTemperature() < 25.0f);
        verify(buildingRepository).save(building);
    }

    @Test
    void shouldDoNothingWhenNoBuildingExists() {
        when(buildingRepository.findAll()).thenReturn(List.of());

        try {
            schedulerService.recalculateBuildingClimate();
        } catch (Exception ex) {
        }

        verify(buildingRepository, never()).save(any());
    }
}
