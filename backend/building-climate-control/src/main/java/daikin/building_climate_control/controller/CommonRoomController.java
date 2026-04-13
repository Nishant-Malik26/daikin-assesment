package daikin.building_climate_control.controller;

import daikin.building_climate_control.model.CommonRoomResponse;
import daikin.building_climate_control.model.CreateCommonRoomRequest;
import daikin.building_climate_control.service.CommonRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commonRooms")
public class CommonRoomController {
    private final CommonRoomService commonRoomService;

    public CommonRoomController(CommonRoomService commonRoomService){
        this.commonRoomService = commonRoomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonRoomResponse> getRoomById(@PathVariable String id){
        return ResponseEntity.ok(commonRoomService.getRoomById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommonRoomResponse>> getAllRooms(){
        return ResponseEntity.ok(commonRoomService.getAllRooms());
    }

    @PostMapping
    public ResponseEntity<CommonRoomResponse> createRoom(@RequestBody CreateCommonRoomRequest commonRoomRequest){
        return ResponseEntity.ok(commonRoomService.createCommonRoom(commonRoomRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable String id){
        commonRoomService.deleteCommonRoom(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }
}
