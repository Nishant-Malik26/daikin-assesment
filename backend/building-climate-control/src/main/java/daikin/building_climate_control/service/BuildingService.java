package daikin.building_climate_control.service;

import daikin.building_climate_control.config.TemperatureConfig;
import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.ApartmentResponse;
import daikin.building_climate_control.model.BuildingResponse;
import daikin.building_climate_control.model.CommonRoomResponse;
import daikin.building_climate_control.model.RoomResponse;
import daikin.building_climate_control.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final TemperatureConfig temperatureConfig;
    public BuildingService(BuildingRepository buildingRepository, TemperatureConfig temperatureConfig){
        this.buildingRepository = buildingRepository;
        this.temperatureConfig = temperatureConfig;
    }

    public BuildingResponse getBuilding(){
        Building building =  buildingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        List<ApartmentResponse> apartmentResponses = building
                .getApartments()
                .stream()
                .map(apartment -> new ApartmentResponse(new RoomResponse(apartment.getId(), apartment.getCurrentTemperature(), apartment.getAcMode().toString()),apartment.getOwnersName(), apartment.getUnitNumber()))
                .toList();
        List<CommonRoomResponse> commonRoomResponses =  building.getRooms()
                .stream()
                .map(room -> new CommonRoomResponse(new RoomResponse(room.getId(),room.getCurrentTemperature(),room.getAcMode().toString()),room.getRoomType())).toList();
        return new BuildingResponse(building.getId(),apartmentResponses , commonRoomResponses,building.getRequestedTemperature());
    }

    public BuildingResponse updateTemperature(Float temperature) {
        Building building = buildingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        building.setRequestedTemperature(temperature);
        for (Apartment apartment : building.getApartments()) {
            apartment.updateMode(temperature, temperatureConfig.getThreshold());
        }

        for (CommonRoom room : building.getRooms()) {
            room.updateMode(temperature, temperatureConfig.getThreshold());
        }
        buildingRepository.save(building);
        return getBuilding();
    }
}
