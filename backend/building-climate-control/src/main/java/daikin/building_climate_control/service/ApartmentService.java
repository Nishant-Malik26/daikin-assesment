package daikin.building_climate_control.service;

import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.ApartmentResponse;
import daikin.building_climate_control.model.CreateApartmentRequest;
import daikin.building_climate_control.model.RoomResponse;
import daikin.building_climate_control.repository.ApartmentRepository;
import daikin.building_climate_control.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final BuildingRepository buildingRepository;
    public ApartmentService(ApartmentRepository apartmentRepository, BuildingRepository buildingRepository){
        this.apartmentRepository = apartmentRepository;
        this.buildingRepository = buildingRepository;
    }

    public List<ApartmentResponse> getAllApartments(){
        List<Apartment> apartments =  apartmentRepository.findAll();

       return apartments.stream().map(apartment ->
            new ApartmentResponse(new RoomResponse(apartment.getId(),apartment.getCurrentTemperature(), apartment.getAcMode().toString()),apartment.getOwnersName(), apartment.getUnitNumber())
         ).toList();

    }

    public ApartmentResponse addApartment(CreateApartmentRequest createApartmentRequest) {
        Building building = buildingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        Apartment apartment = new Apartment();
        apartment.setOwnersName(createApartmentRequest.getOwnersName());
        apartment.setUnitNumber(createApartmentRequest.getUnitNumber());
        building.addApartment(apartment);
        Apartment savedApartment = apartmentRepository.save(apartment);
        return new ApartmentResponse(new RoomResponse(savedApartment.getId(), savedApartment.getCurrentTemperature(), savedApartment.getAcMode().toString()), savedApartment.getOwnersName(), savedApartment.getUnitNumber());
    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }
}
