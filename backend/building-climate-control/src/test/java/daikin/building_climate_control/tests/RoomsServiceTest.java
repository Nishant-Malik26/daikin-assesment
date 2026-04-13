package daikin.building_climate_control.tests;

import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.exception.BadRequestException;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.UpdateRoomRequest;
import daikin.building_climate_control.repository.ApartmentRepository;
import daikin.building_climate_control.repository.CommonRoomRepository;
import daikin.building_climate_control.service.RoomsService;
import daikin.building_climate_control.util.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomsServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private CommonRoomRepository commonRoomRepository;

    private RoomsService roomsService;

    @BeforeEach
    void setUp() {
        roomsService = new RoomsService(apartmentRepository, commonRoomRepository);
    }

    @Test
    void shouldUpdateApartment() {
        Apartment apartment = new Apartment();
        UpdateRoomRequest request = new UpdateRoomRequest();
        request.setOwnersName("Updated Owner");
        request.setUnitNumber("999");

        when(apartmentRepository.existsById(1L)).thenReturn(true);
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(apartment));

        roomsService.updateRooms(1L, request);

        assertEquals("Updated Owner", apartment.getOwnersName());
        assertEquals("999", apartment.getUnitNumber());
        verify(apartmentRepository).save(apartment);
    }

    @Test
    void shouldUpdateCommonRoomType() {
        CommonRoom room = new CommonRoom();
        UpdateRoomRequest request = new UpdateRoomRequest();
        request.setRoomType("LIBRARY");

        when(apartmentRepository.existsById(2L)).thenReturn(false);
        when(commonRoomRepository.existsById(2L)).thenReturn(true);
        when(commonRoomRepository.findById(2L)).thenReturn(Optional.of(room));

        roomsService.updateRooms(2L, request);

        assertEquals(RoomType.LIBRARY, room.getRoomType());
        verify(commonRoomRepository).save(room);
    }

    @Test
    void shouldThrowBadRequestForInvalidCommonRoomType() {
        CommonRoom room = new CommonRoom();
        UpdateRoomRequest request = new UpdateRoomRequest();
        request.setRoomType("BAD_ROOM");

        when(apartmentRepository.existsById(2L)).thenReturn(false);
        when(commonRoomRepository.existsById(2L)).thenReturn(true);
        when(commonRoomRepository.findById(2L)).thenReturn(Optional.of(room));

        assertThrows(BadRequestException.class, () -> roomsService.updateRooms(2L, request));
    }

    @Test
    void shouldThrowWhenRoomNotFound() {
        UpdateRoomRequest request = new UpdateRoomRequest();

        when(apartmentRepository.existsById(3L)).thenReturn(false);
        when(commonRoomRepository.existsById(3L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> roomsService.updateRooms(3L, request));
    }
}
