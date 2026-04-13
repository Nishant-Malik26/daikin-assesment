package daikin.building_climate_control.service;

import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.exception.BadRequestException;
import daikin.building_climate_control.exception.ResourceNotFoundException;
import daikin.building_climate_control.model.UpdateRoomRequest;
import daikin.building_climate_control.repository.ApartmentRepository;
import daikin.building_climate_control.repository.CommonRoomRepository;
import daikin.building_climate_control.util.RoomType;
import org.springframework.stereotype.Service;

@Service
public class RoomsService {
    private final ApartmentRepository apartmentRepository;
    private final CommonRoomRepository commonRoomRepository;

    public RoomsService(
            ApartmentRepository apartmentRepository,
            CommonRoomRepository commonRoomRepository
    ) {
        this.apartmentRepository = apartmentRepository;
        this.commonRoomRepository = commonRoomRepository;
    }

    public void updateRooms(Long id, UpdateRoomRequest request){
        if (apartmentRepository.existsById(id)) {
            Apartment apartment = apartmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Apartment not found"));
            apartment.setOwnersName(request.getOwnersName());
            apartment.setUnitNumber(request.getUnitNumber());
            apartmentRepository.save(apartment);
            return;
        }

        if (commonRoomRepository.existsById(id)) {
            CommonRoom room = commonRoomRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Common room not found"));
            if (request.getRoomType() != null) {
                try {
                    room.setRoomType(RoomType.valueOf(request.getRoomType().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new BadRequestException("Invalid common room type: " + request.getRoomType());
                }
            }
            commonRoomRepository.save(room);
            return;
        }

        throw new ResourceNotFoundException("Room not found");
    }
}
