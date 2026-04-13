package daikin.building_climate_control.service;

import daikin.building_climate_control.config.TemperatureConfig;
import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuildingSchedulerService {
    private final BuildingRepository buildingRepository;
    private final TemperatureConfig temperatureConfig;

    public BuildingSchedulerService(BuildingRepository buildingRepository, TemperatureConfig temperatureConfig) {
        this.buildingRepository = buildingRepository;
        this.temperatureConfig = temperatureConfig;
    }
    @Transactional
    @Scheduled(fixedRateString =  "${simulation.interval.ms}")
    public void recalculateBuildingClimate() {
        Building building = buildingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Building not found"));

        for (Apartment apartment : building.getApartments()) {
            apartment.coolingOrHeating(temperatureConfig.getCoolingRate());
            apartment.updateMode(building.getRequestedTemperature(), temperatureConfig.getThreshold());
        }

        for (CommonRoom room : building.getRooms()) {
            room.coolingOrHeating(temperatureConfig.getCoolingRate());
            room.updateMode(building.getRequestedTemperature(), temperatureConfig.getThreshold());
        }

        buildingRepository.save(building);
    }
}

