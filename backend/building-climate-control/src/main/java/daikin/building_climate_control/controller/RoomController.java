package daikin.building_climate_control.controller;

import daikin.building_climate_control.model.UpdateRoomRequest;
import daikin.building_climate_control.service.RoomsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomsService roomsService;

    public RoomController(RoomsService roomsService){
        this.roomsService = roomsService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoom(@PathVariable Long id, @RequestBody UpdateRoomRequest request) {
        roomsService.updateRooms(id, request);
        return ResponseEntity.ok().build();
    }
}
