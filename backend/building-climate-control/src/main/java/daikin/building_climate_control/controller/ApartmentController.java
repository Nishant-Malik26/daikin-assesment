package daikin.building_climate_control.controller;

import daikin.building_climate_control.model.ApartmentResponse;
import daikin.building_climate_control.model.CreateApartmentRequest;
import daikin.building_climate_control.service.ApartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;
    public ApartmentController(ApartmentService apartmentService){
        this.apartmentService = apartmentService;
    }
    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getApartment (){
        return ResponseEntity.ok(apartmentService.getAllApartments());
    }
    @PostMapping
    public ResponseEntity<ApartmentResponse> addApartment(@RequestBody CreateApartmentRequest createApartmentRequest){
        return ResponseEntity.ok(apartmentService.addApartment(createApartmentRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable String id){
        apartmentService.deleteApartment(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }
}
