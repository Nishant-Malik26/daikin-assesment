package daikin.building_climate_control.tests;

import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.exception.BadRequestException;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.CommonRoomResponse;
import daikin.building_climate_control.model.CreateCommonRoomRequest;
import daikin.building_climate_control.repository.BuildingRepository;
import daikin.building_climate_control.repository.CommonRoomRepository;
import daikin.building_climate_control.service.CommonRoomService;
import daikin.building_climate_control.util.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommonRoomServiceTest {

    @Mock
    private CommonRoomRepository commonRoomRepository;

    @Mock
    private BuildingRepository buildingRepository;

    private CommonRoomService commonRoomService;

    @BeforeEach
    void setUp() {
        commonRoomService = new CommonRoomService(commonRoomRepository, buildingRepository);
    }

    @Test
    void shouldCreateCommonRoom() {
        Building building = new Building();
        CreateCommonRoomRequest request = new CreateCommonRoomRequest();
        request.setRoomType("GYM");

        when(buildingRepository.findAll()).thenReturn(List.of(building));
        when(commonRoomRepository.save(any(CommonRoom.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CommonRoomResponse response = commonRoomService.createCommonRoom(request);

        assertEquals(RoomType.GYM, response.getRoomType());
        verify(commonRoomRepository).save(any(CommonRoom.class));
    }

    @Test
    void shouldThrowBadRequestForInvalidRoomType() {
        Building building = new Building();
        CreateCommonRoomRequest request = new CreateCommonRoomRequest();
        request.setRoomType("BAD_ROOM");

        when(buildingRepository.findAll()).thenReturn(List.of(building));

        assertThrows(BadRequestException.class, () -> commonRoomService.createCommonRoom(request));
    }

    @Test
    void shouldThrowWhenCommonRoomNotFound() {
        when(commonRoomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> commonRoomService.getRoomById("1"));
    }
}
