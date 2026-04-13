package daikin.building_climate_control.service;

import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.exception.BadRequestException;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.CommonRoomResponse;
import daikin.building_climate_control.model.CreateCommonRoomRequest;
import daikin.building_climate_control.model.RoomResponse;
import daikin.building_climate_control.repository.BuildingRepository;
import daikin.building_climate_control.repository.CommonRoomRepository;
import daikin.building_climate_control.util.RoomType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonRoomService {
private final CommonRoomRepository commonRoomRepository;
private final BuildingRepository buildingRepository;
public CommonRoomService(CommonRoomRepository commonRoomRepository, BuildingRepository buildingRepository){
    this.commonRoomRepository = commonRoomRepository;
    this.buildingRepository = buildingRepository;
}

public List<CommonRoomResponse> getAllRooms(){
    return commonRoomRepository.findAll().stream().map(commonRoom -> new CommonRoomResponse(new RoomResponse(commonRoom.getId(),commonRoom.getCurrentTemperature(),commonRoom.getAcMode().toString()),commonRoom.getRoomType())).toList();
}

public CommonRoomResponse getRoomById(String id){
    CommonRoom commonRoom = commonRoomRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    return new CommonRoomResponse(new RoomResponse(commonRoom.getId(),commonRoom.getCurrentTemperature(),commonRoom.getAcMode().toString()),commonRoom.getRoomType());
}

    public CommonRoomResponse createCommonRoom(CreateCommonRoomRequest commonRoomRequest){
        Building building = buildingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        CommonRoom room = new CommonRoom();
        try {
            room.setRoomType(RoomType.valueOf(commonRoomRequest.getRoomType().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid common room type: " + commonRoomRequest.getRoomType());
        }
        building.addRoom(room);
        CommonRoom savedRoom = commonRoomRepository.save(room);
        return new CommonRoomResponse(new RoomResponse(savedRoom.getId(),savedRoom.getCurrentTemperature(),savedRoom.getAcMode().toString()),RoomType.valueOf(commonRoomRequest.getRoomType()));
    }

    public void deleteCommonRoom(Long id) {
        commonRoomRepository.deleteById(id);
    }
}
