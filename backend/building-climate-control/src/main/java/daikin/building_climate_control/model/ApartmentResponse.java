package daikin.building_climate_control.model;

public class ApartmentResponse {
    private String ownersName;
    private RoomResponse roomResponse;
    private String unitNumber;

    public ApartmentResponse(RoomResponse roomResponse, String ownersName, String unitNumber) {
        this.roomResponse = roomResponse;
        this.ownersName = ownersName;
        this.unitNumber = unitNumber;
    }

    public String getOwnersName() {
        return ownersName;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public RoomResponse getRoom() {
        return roomResponse;
    }
}
