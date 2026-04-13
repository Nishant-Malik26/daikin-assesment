package daikin.building_climate_control.tests;

import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.ApartmentResponse;
import daikin.building_climate_control.model.CreateApartmentRequest;
import daikin.building_climate_control.model.RoomResponse;
import daikin.building_climate_control.repository.ApartmentRepository;
import daikin.building_climate_control.repository.BuildingRepository;
import daikin.building_climate_control.service.ApartmentService;
import daikin.building_climate_control.util.ACMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApartmentServiceTest {
    @Mock
    private ApartmentRepository apartmentRepository;
    @Mock
    private BuildingRepository buildingRepository;
    private ApartmentService apartmentService;
    @BeforeEach
    void setUp(){
        apartmentService = new ApartmentService(apartmentRepository, buildingRepository);
    }

    @Test
    void shouldGetApartments(){
        Apartment apartment = new Apartment();
        apartment.setUnitNumber("102");
        apartment.setOwnersName("John Doe");
        apartment.setAcMode(ACMode.HEATING);
        apartment.setCurrentTemperature(22.0f);

        when(apartmentRepository.findAll()).thenReturn(List.of(apartment));
        List<ApartmentResponse> result = apartmentService.getAllApartments();

        assertEquals(1, result.size());
        assertEquals("102", result.get(0).getUnitNumber());
        assertEquals("John Doe", result.get(0).getOwnersName());
        RoomResponse room =  result.get(0).getRoom();
        assertEquals(22.0f,room.getCurrentTemperature());
        assertEquals(ACMode.HEATING.name(), room.getAcMode());
    }

    @Test
    void shouldAddApartment() {
        Building building = new Building();
        CreateApartmentRequest request = new CreateApartmentRequest();
        request.setOwnersName("John");
        request.setUnitNumber("103");

        when(buildingRepository.findAll()).thenReturn(List.of(building));
        when(apartmentRepository.save(any(Apartment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ApartmentResponse response = apartmentService.addApartment(request);

        assertEquals("John", response.getOwnersName());
        assertEquals("103", response.getUnitNumber());

        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);
        verify(apartmentRepository).save(captor.capture());
        assertEquals("John", captor.getValue().getOwnersName());
        assertEquals("103", captor.getValue().getUnitNumber());
    }

    @Test
    void shouldThrowWhenBuildingMissingWhileAddingApartment() {
        CreateApartmentRequest request = new CreateApartmentRequest();
        request.setOwnersName("Jane");
        request.setUnitNumber("103");

        when(buildingRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> apartmentService.addApartment(request));
    }

    @Test
    void shouldDeleteApartment() {
        apartmentService.deleteApartment(10L);

        verify(apartmentRepository).deleteById(10L);
    }
}
