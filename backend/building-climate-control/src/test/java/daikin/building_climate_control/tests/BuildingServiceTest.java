package daikin.building_climate_control.tests;

import daikin.building_climate_control.config.TemperatureConfig;
import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.BuildingResponse;
import daikin.building_climate_control.repository.BuildingRepository;
import daikin.building_climate_control.service.BuildingService;
import daikin.building_climate_control.util.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuildingServiceTest {
    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private TemperatureConfig temperatureConfig;
    private BuildingService buildingService;

    @BeforeEach
    void setUp() {
        buildingService = new BuildingService(buildingRepository, temperatureConfig);
    }

    @Test
    void shouldReturnBuildingResponse(){
        Building building = new Building();
        building.setRequestedTemperature(25.0f);

        Apartment apartment = new Apartment();
        apartment.setOwnersName("John");
        apartment.setUnitNumber("101");
        building.addApartment(apartment);

        CommonRoom room = new CommonRoom();
        room.setRoomType(RoomType.GYM);
        building.addRoom(room);

        when(buildingRepository.findAll()).thenReturn(List.of(building));

        BuildingResponse response = buildingService.getBuilding();

        assertEquals(25.0f, response.getRequestedTemperature());
        assertEquals(1, response.getApartmentResponses().size());
        assertEquals(1, response.getRooms().size());
        assertEquals("101", response.getApartmentResponses().get(0).getUnitNumber());
        assertEquals(RoomType.GYM, response.getRooms().get(0).getRoomType());
    }

    @Test
    void shouldThrowWhenBuildingNotFound() {
        when(buildingRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> buildingService.getBuilding());
    }

    @Test
    void shouldUpdateTemperatureAndSaveBuilding() {
        Building building = new Building();
        building.setRequestedTemperature(25.0f);

        Apartment apartment = new Apartment();
        apartment.setCurrentTemperature(18.0f);
        building.addApartment(apartment);

        CommonRoom room = new CommonRoom();
        room.setCurrentTemperature(28.0f);
        room.setRoomType(RoomType.GYM);
        building.addRoom(room);

        when(buildingRepository.findAll()).thenReturn(List.of(building));
        when(temperatureConfig.getThreshold()).thenReturn(0.5f);

        BuildingResponse response = buildingService.updateTemperature(22.0f);

        assertEquals(22.0f, response.getRequestedTemperature());
        verify(buildingRepository, atLeastOnce()).save(building);
    }
}
