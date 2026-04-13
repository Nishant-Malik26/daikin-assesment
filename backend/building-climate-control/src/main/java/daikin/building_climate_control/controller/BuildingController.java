package daikin.building_climate_control.controller;

import daikin.building_climate_control.model.BuildingResponse;
import daikin.building_climate_control.model.TemperatureBody;
import daikin.building_climate_control.service.BuildingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping()
    public ResponseEntity<BuildingResponse> getBuilding(){
        return ResponseEntity.ok(buildingService.getBuilding());
    }

    @PutMapping("/temperature")
    public ResponseEntity<BuildingResponse> updateTemperature(@RequestBody TemperatureBody temperature){
        return ResponseEntity.ok(buildingService.updateTemperature(temperature.getTemperature()));
    }

}
