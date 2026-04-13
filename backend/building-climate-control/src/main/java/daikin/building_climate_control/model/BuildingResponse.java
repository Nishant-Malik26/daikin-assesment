package daikin.building_climate_control.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

public class BuildingResponse {

    private Long id;
    private List<ApartmentResponse> apartmentResponses;
    private List<CommonRoomResponse> rooms;
    private float requestedTemperature;

    public BuildingResponse(List<ApartmentResponse> apartmentResponses, List<CommonRoomResponse> rooms){
        this.apartmentResponses = apartmentResponses;
        this.rooms = rooms;
        this.requestedTemperature = 20.0F;
    }

    public BuildingResponse(Long id, List<ApartmentResponse> apartmentResponses, List<CommonRoomResponse> rooms, Float requestedTemperature){
        this.id = id;
        this.apartmentResponses = apartmentResponses;
        this.rooms = rooms;
        this.requestedTemperature = requestedTemperature;
    }

    public Long getId() {
        return id;
    }

    public List<ApartmentResponse> getApartmentResponses() {
        return apartmentResponses;
    }

    public List<CommonRoomResponse> getRooms() {
        return rooms;
    }

    public float getRequestedTemperature() {
        return requestedTemperature;
    }
}
