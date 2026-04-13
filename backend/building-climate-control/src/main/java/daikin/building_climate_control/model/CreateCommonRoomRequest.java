package daikin.building_climate_control.model;

import daikin.building_climate_control.util.RoomType;

public class CreateCommonRoomRequest {
    private String roomType;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
