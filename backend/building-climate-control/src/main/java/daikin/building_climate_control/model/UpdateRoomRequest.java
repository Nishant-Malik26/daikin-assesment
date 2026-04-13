package daikin.building_climate_control.model;

public class UpdateRoomRequest {
    private String ownersName;
    private String unitNumber;
    private String roomType;

    public String getOwnersName() {
        return ownersName;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setOwnersName(String ownersName) {
        this.ownersName = ownersName;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
