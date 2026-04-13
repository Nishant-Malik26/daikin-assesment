package daikin.building_climate_control.model;

import daikin.building_climate_control.util.RoomType;

public class CommonRoomResponse {
    private RoomType roomType;
    private RoomResponse roomResponse;

    public CommonRoomResponse(RoomResponse roomResponse, RoomType roomType){
        this.roomType = roomType;
        this.roomResponse = roomResponse;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public RoomResponse getRoom() {
        return roomResponse;
    }
}
